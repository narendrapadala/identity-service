package corp.asbp.platform.is.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.Role;
import corp.asbp.platform.is.model.RoleCategory;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findFirstById(Long roleId);
	
	List<Role> findAllByRoleCategory(RoleCategory roleCategory);
}
