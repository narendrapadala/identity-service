package corp.asbp.platform.is.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.RoleCategory;


@Repository
public interface RoleCategoryRepository extends JpaRepository<RoleCategory, Long> {
	RoleCategory findFirstById(Long roleCategoryId);
}
