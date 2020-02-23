package corp.asbp.platform.is.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import corp.asbp.platform.is.dto.AddUserRoleDto;
import corp.asbp.platform.is.dto.LoginDto;
import corp.asbp.platform.is.dto.UserResponseDto;
import corp.asbp.platform.is.dto.UsersProfileDto;

import corp.asbp.platform.is.exception.OAuthResourceNotFoundException;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.User;
import corp.asbp.platform.is.payload.GenericResponseDto;
import corp.asbp.platform.is.repository.UserRepository;
import corp.asbp.platform.is.security.CurrentUser;
import corp.asbp.platform.is.security.UserPrincipal;
import corp.asbp.platform.is.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
   // @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new OAuthResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
    
    @GetMapping("/hello")
    public User hello() {
        return userRepository.findById(1L)
                .orElseThrow(() -> new OAuthResourceNotFoundException("User", "id",1L));
    }
	@GetMapping("/all")
	public GenericResponseDto<Page<User>> getAllUsers(@RequestParam(required = false) String searchColumn,
			@RequestParam(required = false) String searchValue, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request,
				userService.getAllUsers(PageRequest.of(page, size))).build();
	}

	@GetMapping("/getUser")
	public GenericResponseDto<User> getUser(@RequestParam Long userId, HttpServletRequest request) {

		return new GenericResponseDto.GenericResponseDtoBuilder<>(request, userService.getUser(userId)).build();
	}

	@PostMapping("/addUser")
	public GenericResponseDto<User> addUser(@RequestBody User user, HttpServletRequest request) throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("User saved sucessfully", request,
				userService.saveUser(user)).build();
	}
	
	@PostMapping("/addUserRole")
	public GenericResponseDto<List<ModuleConfigMapping>> addUserRole(@RequestBody AddUserRoleDto userRole, HttpServletRequest request) throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("User roles saved sucessfully", request,
				userService.addUserRole(userRole)).build();
	}

	@PutMapping("/updateUser")
	public GenericResponseDto<User> updateUser(@RequestBody User user, HttpServletRequest request) throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("User updated sucessfully", request,
				userService.updateUser(user)).build();
	}

	@DeleteMapping("/deleteUser")
	public GenericResponseDto<Boolean> deleteUser(@RequestParam Long userId, HttpServletRequest request) {
		userService.deleteUser(userId);
		return new GenericResponseDto.GenericResponseDtoBuilder<>("User deleted sucessfully", request, true).build();
	}

	@GetMapping("/getProfile")
	public GenericResponseDto<UsersProfileDto> getProfile(@RequestParam Long userId, HttpServletRequest request) {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request, userService.getProfile(userId)).build();
	}
	
	@PostMapping("/login")
	public GenericResponseDto<UserResponseDto> login(@RequestBody LoginDto login, HttpServletRequest request) throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("loged in sucessfully", request,
				userService.login(login)).build();
	}
	@GetMapping("/logout")
	public GenericResponseDto<Boolean> logout(@RequestParam String session, HttpServletRequest request) {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request, userService.logout(session)).build();
	}

}
