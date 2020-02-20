package corp.asbp.platform.is.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.ModuleConfigMapping;


@Repository
public interface ModuleConfigMappingRepository extends JpaRepository<ModuleConfigMapping, Long> {
	
	List<ModuleConfigMapping> findByRoleIdIn(List<Long> roleIds);

	List<ModuleConfigMapping> findByRoleId(Long roleId);

	List<ModuleConfigMapping> findAllByIdRoleIdAndIdClientId(Long roleId,Long clientId);

	void deleteByIdRoleIdAndIdClientId(Long roleId, Long clientId);

	List<ModuleConfigMapping> findAllByModuleFeatureIdIn(List<Long> featureIds);

	

}
