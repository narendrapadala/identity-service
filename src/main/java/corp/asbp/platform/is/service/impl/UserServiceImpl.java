package corp.asbp.platform.is.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

//import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import corp.asbp.platform.is.dto.AddUserRoleDto;
import corp.asbp.platform.is.dto.LoginDto;
import corp.asbp.platform.is.dto.UserResponseDto;
import corp.asbp.platform.is.dto.UsersProfileDto;
import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.Role;
import corp.asbp.platform.is.model.User;
import corp.asbp.platform.is.model.UserRoleMapping;
import corp.asbp.platform.is.repository.ModuleConfigMappingRepository;
import corp.asbp.platform.is.repository.RoleRepository;
import corp.asbp.platform.is.repository.UserRepository;
import corp.asbp.platform.is.repository.UserRoleMappingRepository;
import corp.asbp.platform.is.service.RolesService;
import corp.asbp.platform.is.service.UserService;
import corp.asbp.platform.is.util.EnvironmentProperties;




/**
 * @author Narendra
 *
 */
@Service
@Transactional
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {

	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private EnvironmentProperties properties;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private UserRoleMappingRepository userRoleMappingRepo;

	@Autowired
	ModuleConfigMappingRepository moduleConfigMappingRepo;

	@Override
	public void deleteUser(Long userId) {
		userRepo.deleteById(userId);
	}

	@Override
	public User saveUser(User user) throws ASBPException {
		user.setCreatedAt(System.currentTimeMillis());
		user.setCreatedBy(1L);
		user.setModifiedBy(1L);
		user.setModifiedAt(System.currentTimeMillis());
		return userRepo.save(user);
	}

	@Override
	public Page<User> getAllUsers(Pageable pageable) {
		return userRepo.findAll(pageable);
	}

	@Override
	public User getUser(Long userId) {
		return userRepo.findFirstById(userId);
	}

	@Override
	public User updateUser(User user) throws Exception {
		User usr = userRepo.findById(user.getId())
				.orElseThrow(() -> new Exception("user with id " + user.getId() + " doesn't exist"));
		usr.setModifiedBy(1L);
		usr.setModifiedAt(System.currentTimeMillis());
		return userRepo.save(usr);
	}

	// user role mapping
	@Override
	public UserRoleMapping saveUserRoleMapping(UserRoleMapping userRoleMapping) throws ASBPException {

		User usr = userRepo.findById(userRoleMapping.getId().getUserId()).orElseThrow(
				() -> new ASBPException("user with id " + userRoleMapping.getId().getUserId() + " doesn't exist"));

		Role r = roleRepo.findById(userRoleMapping.getId().getRoleId()).orElseThrow(
				() -> new ASBPException("role with id " + userRoleMapping.getId().getRoleId() + " doesn't exist"));

		userRoleMapping.setCreatedAt(System.currentTimeMillis());
		userRoleMapping.setCreatedBy(1L);
		userRoleMapping.setModifiedBy(1L);
		userRoleMapping.setModifiedAt(System.currentTimeMillis());

		return userRoleMappingRepo.save(userRoleMapping);
	}

	@Override
	public UserRoleMapping updateUserRoleMapping(UserRoleMapping userRoleMapping) throws Exception {
		User usr = userRepo.findById(userRoleMapping.getId().getUserId()).orElseThrow(
				() -> new Exception("user with id " + userRoleMapping.getId().getUserId() + " doesn't exist"));

		Role r = roleRepo.findById(userRoleMapping.getId().getRoleId()).orElseThrow(
				() -> new Exception("role with id " + userRoleMapping.getId().getRoleId() + " doesn't exist"));

		userRoleMapping.setModifiedBy(1L);
		userRoleMapping.setModifiedAt(System.currentTimeMillis());

		return userRoleMappingRepo.save(userRoleMapping);
	}

	@Override
	public void deleteRoleMappingByUser(Long userId) {
		// TODO Auto-generated method stub
		userRoleMappingRepo.deleteByIdUserId(userId);

	}

	@Override
	public UsersProfileDto getProfile(Long userId) {

		UsersProfileDto userProfile = new UsersProfileDto();
		UsersProfileDto.User usr = new UsersProfileDto.User();
		User user =getUser(userId);
		//check
		if(user!=null) {
			usr.setId(user.getId());
			usr.setUserGroupId(user.getUserGroupId());
			usr.setParentUserId(user.getParentUserId());
			usr.setAccountType(user.getAccountType());
			usr.setFirstName(user.getFirstName());
			usr.setLastName(user.getLastName());
			usr.setEmail(user.getEmail());
			usr.setAddressLine1(user.getAddressLine1());
			usr.setAddressLine2(user.getAddressLine2());
			usr.setCity(user.getCity());
			usr.setState(user.getState());
			usr.setCountry(user.getCountry());
			usr.setPhoneNo(user.getPhoneNo());
			usr.setCustomerCompanyName(user.getCustomerCompanyName());
			usr.setCreditLimit(user.getCreditLimit());
			
			userProfile.setUser(usr);
		}

		
		List<String> roles = new ArrayList<String>();
		List<String> modules = new ArrayList<String>();
		List<String> features = new ArrayList<String>();
		
		List<ModuleConfigMapping> moduleConfigMappings = getUserRoles(userId);
		
		//check
		if(moduleConfigMappings !=null )  {
			//loop
			for(ModuleConfigMapping mconf : moduleConfigMappings) {
				//check
				if(!roles.contains(mconf.getRole().getName())) {
					roles.add(mconf.getRole().getName());
				}
				//check
				if(!roles.contains(mconf.getRole().getName())) {
					roles.add(mconf.getRole().getName());
				}
				//check
				if(!features.contains(mconf.getModuleFeature().getName())) {
					features.add(mconf.getModuleFeature().getName());
				}
				//check
				if(!modules.contains(mconf.getModule().getName())) {
					modules.add(mconf.getModule().getName());
				}
			}
			
		}
		

		userProfile.setRoles(roles);		
		userProfile.setModules(modules);
		userProfile.setFeatures(features);
		
		return userProfile;
		

	}

	@Override
	public List<ModuleConfigMapping> getUserRoles(Long userId) {

		List<UserRoleMapping> usrMap = userRoleMappingRepo.findAllByIdUserId(userId);

		List<Long> roleIds = new ArrayList<Long>();

		if (usrMap != null) {
			for (UserRoleMapping urm : usrMap) {
				//
				roleIds.add(urm.getId().getRoleId());
			}
			// check
			if (roleIds.size() > 0) {
				List<ModuleConfigMapping> mcMap = moduleConfigMappingRepo.findByRoleIdIn(roleIds);

				return mcMap;
			}
		}
		return null;
	}

	@Override
	public List<ModuleConfigMapping> addUserRole(AddUserRoleDto userRole) {

		// check
		if (userRole.getRolesIds().size() > 0) {
			List<UserRoleMapping> usrMap = userRoleMappingRepo.findAllByIdUserId(userRole.getUserId());
			// check
			if (usrMap != null) {
				deleteRoleMappingByUser(userRole.getUserId());
			}
			// loop
			for (Long roleId : userRole.getRolesIds()) {

				User usr = new User();
				usr.setId(userRole.getUserId());
				Role role = new Role();
				role.setId(roleId);
				UserRoleMapping urm = new UserRoleMapping(usr, role);
				saveUserRoleMapping(urm);
			}

			return moduleConfigMappingRepo.findByRoleIdIn(userRole.getRolesIds());
		}
		return null;
	}

	@Override
	public UserResponseDto login(LoginDto login) {
		
		User usr = userRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		System.out.println(usr.toString());
		if (!usr.equals(null)) {
			String session = UUID.randomUUID().toString();
			UserResponseDto res= new UserResponseDto();
			res.setEmail(usr.getEmail());
			res.setId(usr.getId());
			res.setSessionId(session);
			
			try {
				redisTemplate.opsForValue().set(session, objectMapper.writeValueAsString(res));
				if (properties.getSessionTimeout() > 0)
					redisTemplate.expire(session, properties.getSessionTimeout(),
							TimeUnit.SECONDS);
				
				return res;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean logout(String sessionId) {
		/*
		if(StringUtils.isNotEmpty(sessionId)) {
			if (!redisTemplate.delete(sessionId)) {
				log.error("Couldn't log  user out, sessionId: {}",sessionId);
				return false;
			}else{
				return true;
			}
		}
		*/
		return null;
	}

}
