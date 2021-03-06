package corp.asbp.platform.is.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.Api;
import corp.asbp.platform.is.model.ApiModuleFeatureMapping;
import corp.asbp.platform.is.model.ModuleFeature;



@Repository
public interface ApiModuleFeatureMappingRepository extends JpaRepository<ApiModuleFeatureMapping, Long> {
	List<ApiModuleFeatureMapping> findByApi(Api api);
	
	List<ApiModuleFeatureMapping> findAllByModuleFeatureIn(List<ModuleFeature> moduleFeature);

}

