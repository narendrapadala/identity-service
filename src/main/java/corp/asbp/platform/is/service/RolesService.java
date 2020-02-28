/**
 * 
 */
package corp.asbp.platform.is.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import corp.asbp.platform.is.dto.AssignRolesAndResponsibilitesDto;
import corp.asbp.platform.is.dto.GuestApi;
import corp.asbp.platform.is.dto.RoleAndResponsibilityMappingDto;
import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.Role;
import corp.asbp.platform.is.model.RoleCategory;



/**
 * @author Narendra
 *
 */
public interface RolesService {

	Role saveRole(Role role) throws ASBPException;

	Role updateRole(Role role) throws Exception;

	void deleteRole(Long roleId);
	
	Role getRole(Long roleId);
	
	GuestApi getGuestAllRoleApis();

	Page<Role> getAllRoles(String searchColumn, String searchValue, Pageable pageable);
	
	Page<RoleCategory> getAllRoleCategories(Pageable pageable);
	
	RoleCategory saveRoleCategory(RoleCategory roleCategory) throws ASBPException;

	RoleCategory updateRoleCategory(RoleCategory roleCategory) throws Exception;

	void deleteRoleCategory(Long roleId);
	
	RoleAndResponsibilityMappingDto getRoleAndResponsibilities();
	
	RoleCategory getRoleCategory(Long roleId);
	
	List<ModuleConfigMapping> getUserRolesModules(Long roleId,Long clientId);
	
	List<ModuleConfigMapping> addRolesAndResponsibilities(AssignRolesAndResponsibilitesDto assinRoles);
	
	
	



}
