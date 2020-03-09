package corp.asbp.platform.is.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

//import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import corp.asbp.platform.is.dto.GuestApi;
import corp.asbp.platform.is.dto.SsHeader;
import corp.asbp.platform.is.dto.UserResponseDto;
import corp.asbp.platform.is.dto.UsersProfileDto;
import corp.asbp.platform.is.exception.AccessForbiddenException;
import corp.asbp.platform.is.exception.ClientVersionUpgradeException;
import corp.asbp.platform.is.exception.ResourceNotFoundException;
import corp.asbp.platform.is.exception.UnAuthorizedException;
import corp.asbp.platform.is.model.Api;
import corp.asbp.platform.is.model.ApiModuleFeatureMapping;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.UserRoleMapping;
import corp.asbp.platform.is.repository.ApiModuleFeatureMappingRepository;
import corp.asbp.platform.is.repository.ApiRepository;
import corp.asbp.platform.is.repository.ModuleConfigMappingRepository;
import corp.asbp.platform.is.repository.UserRoleMappingRepository;
import corp.asbp.platform.is.service.AuthorizationService;
import corp.asbp.platform.is.service.RolesService;
import corp.asbp.platform.is.util.CustomUtil;

@Service
@Transactional
@SuppressWarnings("unused")
public class AuthorizationServiceImpl implements AuthorizationService {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private ApiRepository apiRepo;

	@Autowired
	private ApiModuleFeatureMappingRepository apiModuleFeatureMappingRepo;

	@Autowired
	private ModuleConfigMappingRepository moduleConfigMappingRepo;

	@Autowired
	private UserRoleMappingRepository userRoleMappingRepo;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private RolesService rolesService;

	@Override
	public UsersProfileDto validateUser(SsHeader ssHeader, String uri, HttpMethod reqMethod)
			throws AccessForbiddenException, UnAuthorizedException, ResourceNotFoundException, IOException,
			ClientVersionUpgradeException {
		UserResponseDto usr = new UserResponseDto();
		try {

			GuestApi guestApi;

			String guest = redisTemplate.opsForValue().get("guest_apis");

			if (guest != null) {
				guestApi = mapper.readValue(guest, GuestApi.class);
			} else {
				//update
				guestApi = rolesService.getGuestAllRoleApis();
				redisTemplate.opsForValue().set("guest_apis", mapper.writeValueAsString(guestApi));
			}

			// guest
			if (guestApi != null && guestApi.getGuestApi().size() > 0) {
				// loop
				for (String api : guestApi.getGuestApi()) {
					System.out.println("Guest API" + api);
				}
			}
			
			if (guestApi.getGuestApi() != null && guestApi.getGuestApi().size() > 0 && guestApi.getGuestApi().contains(uri)) {				
				//return
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("Got request to validate user: " + ssHeader);
		LOGGER.info("api to validate: " + uri);
		LOGGER.info("api to validate: " + reqMethod);

		System.out.println(ssHeader);
		System.out.println(uri);

		UsersProfileDto user = getUserFromSession(ssHeader.getSessionId());
		usr.setEmail(user.getUser().getEmail());
		usr.setId(user.getUser().getId());
		usr.setSessionId(ssHeader.getSessionId());

		Boolean b = getApiAccessRole(usr, uri, reqMethod);
		// return
		return user;
	}

	@Override
	public GuestApi getGuestApisFromSession(String sessionId) {

		GuestApi guestApi = new GuestApi();

		if (!CustomUtil.isEmptyString(sessionId)) {

			try {
				String guest = redisTemplate.opsForValue().get(sessionId + "_guest");

				if (guest != null) {
					guestApi = mapper.readValue(guest, GuestApi.class);
				}
				// guest
				if (guestApi != null && guestApi.getGuestApi().size() > 0) {
					// loop
					for (String api : guestApi.getGuestApi()) {
						System.out.println("Guest API" + api);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// return
		return guestApi;
	}

	@Override
	public UsersProfileDto getUserFromSession(String sessionId) throws UnAuthorizedException, IOException {

		UsersProfileDto user = new UsersProfileDto();

		if (!CustomUtil.isEmptyString(sessionId)) {
			LOGGER.info("Get user object via session from redis...");
			String s = redisTemplate.opsForValue().get(sessionId);

			if (s != null) {
				user = mapper.readValue(s, UsersProfileDto.class);
			} else {
				throw new UnAuthorizedException("Invalid session. Please relogin.");
			}
		}
		return user;
	}

	private Boolean getApiAccessRole(UserResponseDto userResponseDto, String requestUri, HttpMethod reqMethod)
			throws ResourceNotFoundException, AccessForbiddenException {
		LOGGER.info("Get api from repository...");

		Api api = apiRepo.findFirstByNameAndType(requestUri, reqMethod);

		if (api == null) {
			LOGGER.error("This api doesn't exist: " + requestUri);
			throw new ResourceNotFoundException("This api doesn't exist: " + requestUri);
		}

		// get guest apis from session
		GuestApi gApi = getGuestApisFromSession(userResponseDto.getSessionId());
		// check the url available in guest apis
		if (gApi.getGuestApi() != null && gApi.getGuestApi().size() > 0 && gApi.getGuestApi().contains(requestUri)) {
			return true;
		}

		List<ApiModuleFeatureMapping> mf = apiModuleFeatureMappingRepo.findByApi(api);

		if (mf == null)
			throw new AccessForbiddenException("No module features are mapped with this api: " + api.getName());

		// feature
		List<Long> featureIds = new ArrayList<>();
		// loop
		for (ApiModuleFeatureMapping amfm : mf) {
			// check and remove duplicate
			if (featureIds.size() == 0) {
				featureIds.add(amfm.getModuleFeature().getId());
			} else if (featureIds.size() > 0 && !featureIds.contains(amfm.getModuleFeature().getId())) {
				featureIds.add(amfm.getModuleFeature().getId());
			}
		}

		List<ModuleConfigMapping> mconfig = moduleConfigMappingRepo.findAllByModuleFeatureIdIn(featureIds);

		if (mconfig == null)
			throw new AccessForbiddenException("No roles are mapped with this api: " + api.getName());

		// feature
		List<Long> roleIds = new ArrayList<>();
		// loop
		for (ModuleConfigMapping mconf : mconfig) {
			System.out.println("api role : " + mconf.toString());
			// check and remove duplicate
			if (roleIds.size() == 0) {
				roleIds.add(mconf.getId().getRoleId());
				System.out.println("api role : " + mconf.getId().getRoleId());
			} else if (roleIds.size() > 0 && !roleIds.contains(mconf.getId().getRoleId())) {
				System.out.println("api role : " + mconf.getId().getRoleId());
				roleIds.add(mconf.getId().getRoleId());
			}
		}

		List<UserRoleMapping> userRoles = userRoleMappingRepo.findAllByIdUserId(userResponseDto.getId());

		// check
		if (userRoles != null) {
			int flag = 0;
			// loop
			for (UserRoleMapping userRole : userRoles) {
				System.out.println("user role : " + userRole.getId().getRoleId());
				// check
				if (roleIds.size() > 0 && roleIds.contains(userRole.getId().getRoleId())) {
					flag = 1;
				}
			}

			// check
			if (flag == 0)
				throw new AccessForbiddenException(
						"The user doesn't have appropriate role to access this api: " + requestUri);
			else
				return true;
		} else {
			throw new AccessForbiddenException(
					"The user doesn't have appropriate role to access this api: " + requestUri);

		}

	}

	@Override
	public String test() {
		return demo();
	}

	@Cacheable("test")
	public String demo() {
		System.out.println("Test serivce");
		return "Narendra Test";
	}

}
