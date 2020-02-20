package corp.asbp.platform.is.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.UserRoleMapping;


@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, Long> {
	
	List<UserRoleMapping> findAllByIdUserId(Long userId);

	void deleteByIdUserId(Long userId);
}
