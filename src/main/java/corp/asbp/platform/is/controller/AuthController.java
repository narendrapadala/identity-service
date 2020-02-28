package corp.asbp.platform.is.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import corp.asbp.platform.is.dto.DecriptionUserResponseDto;
import corp.asbp.platform.is.dto.GuestApi;
import corp.asbp.platform.is.dto.ProcessDecryptionDto;
import corp.asbp.platform.is.dto.UsersProfileDto;
import corp.asbp.platform.is.enumerations.AuthProvider;
import corp.asbp.platform.is.enumerations.CommonStatus;
import corp.asbp.platform.is.exception.AccessForbiddenException;
import corp.asbp.platform.is.exception.BadRequestException;
import corp.asbp.platform.is.exception.ClientVersionUpgradeException;
import corp.asbp.platform.is.exception.OAuth2AuthenticationProcessingException;
import corp.asbp.platform.is.exception.ResourceNotFoundException;
import corp.asbp.platform.is.exception.UnAuthorizedException;
import corp.asbp.platform.is.model.User;
import corp.asbp.platform.is.payload.ApiResponse;
import corp.asbp.platform.is.payload.AuthResponse;
import corp.asbp.platform.is.payload.GenericResponseDto;
import corp.asbp.platform.is.payload.LoginRequest;
import corp.asbp.platform.is.payload.SignUpRequest;
import corp.asbp.platform.is.repository.UserRepository;
import corp.asbp.platform.is.security.TokenProvider;
import corp.asbp.platform.is.security.UserPrincipal;
import corp.asbp.platform.is.service.AuthorizationService;
import corp.asbp.platform.is.service.DecryptionApiSettingsService;
import corp.asbp.platform.is.service.RolesService;
import corp.asbp.platform.is.service.UserService;
import corp.asbp.platform.is.util.CustomUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private DecryptionApiSettingsService decryptionApiService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private RolesService rolesService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		String session = userService.setRedisSession(userPrincipal.getId());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new AuthResponse(token, session));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email address already in use.");
		}

		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);
		user.setStatus(CommonStatus.ENABLED);
		user.setCreatedAt(CustomUtil.currentTimeStamp());
		user.setModifiedAt(CustomUtil.currentTimeStamp());
		user.setCreatedBy(1L);
		user.setModifiedBy(1L);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully@"));
	}

	@RequestMapping("/tokenAuth")
	public ResponseEntity<?> redirectToExternalUrl(@RequestParam Long vendor, @RequestParam String ecryptionStr,
			@RequestParam String redirectUri, HttpServletRequest request) throws URISyntaxException, IOException {

		// set description object
		ProcessDecryptionDto decryption = new ProcessDecryptionDto();
		decryption.setEcryptionStr(ecryptionStr);
		decryption.setRedirectUri(redirectUri);
		decryption.setVendor(vendor);

		// decrypt token
		DecriptionUserResponseDto tokenUser = decryptionApiService.processDecryption(decryption);

		Optional<User> userOptional = userRepository.findByEmail(tokenUser.getEmail());
		User user;
		// check
		if (userOptional.isPresent()) {
			user = userOptional.get();
			if (!user.getProvider().equals("token")) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your "
								+ user.getProvider() + " account to login.");
			}
			//vendor id is parent id
			user.setParentUserId(vendor);
			user = userService.updateExistingUser(user, tokenUser);
		} else {
			user = userService.registerNewUser(tokenUser,vendor);
		}

		// set pass - to do password encryption
		String tokenPass = tokenUser.getUniqueId();
		// login
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(tokenUser.getEmail(), tokenPass));

		String session = userService.setRedisSession(user.getId());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);

		String url = UriComponentsBuilder.fromUriString(redirectUri).queryParam("token", token)
				.queryParam("session", session).build().toUriString();

		// set url
		URI targetUrl = new URI(url);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(targetUrl);

		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

	@PostMapping("/validateSession")
	@ResponseBody
	public GenericResponseDto<UsersProfileDto> validate(HttpServletRequest request, HttpServletResponse response)
			throws AccessForbiddenException, UnAuthorizedException, ResourceNotFoundException, IOException,
			ClientVersionUpgradeException {

		UsersProfileDto user = authorizationService.validateUser(CustomUtil.getSsHeader(request),
				CustomUtil.getRequestUri(request), CustomUtil.getRequestMethod(request));
		return new GenericResponseDto<UsersProfileDto>(user, request);
	}
	
	@GetMapping("/getUserFromSession")
	public GenericResponseDto<UsersProfileDto> getUserFromSession(@RequestParam String sessionId, HttpServletRequest request) throws UnAuthorizedException, IOException {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request, authorizationService.getUserFromSession(sessionId)).build();
	}
	
	@GetMapping("/getGuestAllRoleApis")
	public GenericResponseDto<GuestApi> getGuestAllRoleApis(HttpServletRequest request) throws UnAuthorizedException, IOException {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request, rolesService.getGuestAllRoleApis()).build();
	}

}
