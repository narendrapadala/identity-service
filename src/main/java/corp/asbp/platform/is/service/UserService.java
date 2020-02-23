/**
 * 
 */
package corp.asbp.platform.is.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import corp.asbp.platform.is.dto.AddUserRoleDto;
import corp.asbp.platform.is.dto.DecriptionUserResponseDto;
import corp.asbp.platform.is.dto.LoginDto;
import corp.asbp.platform.is.dto.UserResponseDto;
import corp.asbp.platform.is.dto.UsersProfileDto;
import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.User;
import corp.asbp.platform.is.model.UserRoleMapping;





/**
 * @author Narendra
 *
 */
public interface UserService {

	User saveUser(User user) throws ASBPException;

	User updateUser(User user) throws Exception;

	void deleteUser(Long userId);

	Page<User> getAllUsers(Pageable pageable);

	User getUser(Long userId);
	
	UserResponseDto login(LoginDto login);
	
	UsersProfileDto getProfile(Long userId);
	
	List<ModuleConfigMapping> getUserRoles(Long userId);

	
	
	//user role mapping
	UserRoleMapping saveUserRoleMapping(UserRoleMapping userRoleMapping) throws ASBPException;

	UserRoleMapping updateUserRoleMapping(UserRoleMapping userRoleMapping) throws Exception;
	
	
	
	//user role
	//List<UserRoleMapping> getRoleMappingByUser(Long userId) throws ASBPException;
	
	void deleteRoleMappingByUser(Long userId);

	List<ModuleConfigMapping> addUserRole(AddUserRoleDto userRole);

	Boolean logout(String session);

	
	//description
	
	User registerNewUser(DecriptionUserResponseDto tokenUser,Long vendor);
	
	User updateExistingUser(User existingUser, DecriptionUserResponseDto tokenUser);

	String setRedisSession(Long userId);
	

	


}
