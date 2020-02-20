/**
 * 
 */
package corp.asbp.platform.is.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import corp.asbp.platform.is.dto.FeatureIdsRequestDto;
import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.Api;
import corp.asbp.platform.is.model.ApiModuleFeatureMapping;
import corp.asbp.platform.is.model.ModuleConfigMapping;


/**
 * @author Narendra
 *
 */
public interface ApiService {

	Api saveApi(Api api) throws ASBPException;

	Api updateApi(Api api) throws Exception;

	void deleteApi(Long apiId);

	void bulkAddApiswithRoles(MultipartFile file, List<String> headers) throws ASBPException;

	Page<Api> getAllApis(String searchColumn, String searchValue, Pageable pageable);

	Api getApi(Long apiId);

	Map<String, String> getBulkAddApiSTemplate();

	// api mappings
	ApiModuleFeatureMapping saveApiModuleFeatureMapping(ApiModuleFeatureMapping apiModuleFeatureMapping)
			throws ASBPException;

	ApiModuleFeatureMapping updateApiModuleFeatureMapping(ApiModuleFeatureMapping apiModuleFeatureMapping)
			throws Exception;

	void deleteApiModuleFeatureMapping(Long apiModuleFeatureMappingId);

	ApiModuleFeatureMapping getApiModuleFeatureMapping(Long apiModuleFeatureMappingId);

	Page<ApiModuleFeatureMapping> getAllApiModuleFeatureMappings(String searchColumn, String searchValue,
			Pageable pageable);

	List<ApiModuleFeatureMapping> getApiFeatureMappingById(Long apiId);
	
	List<ModuleConfigMapping> getApiModuleConfigMappingFeatureIds(FeatureIdsRequestDto featureIds);
	
	

}
