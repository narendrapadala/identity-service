package corp.asbp.platform.is.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.ModuleFeature;


@Repository
public interface ModuleFeatureRepository extends JpaRepository<ModuleFeature, Long> {

	ModuleFeature findFirstById(Long moduleFeatureId);

	List<ModuleFeature> findAllByModuleId(Long moduleId);
	Page<ModuleFeature> findAllByModuleId(Long moduleId,Pageable pageable);
	
}
