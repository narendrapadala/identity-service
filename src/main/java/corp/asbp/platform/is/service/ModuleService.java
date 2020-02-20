/**
 * 
 */
package corp.asbp.platform.is.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.Module;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.ModuleFeature;

/**
 * @author Narendra
 *
 */
public interface ModuleService {

	Module saveModule(Module module) throws ASBPException;

	Module updateModule(Module module) throws Exception;

	void deleteModule(Long moduleId);

	Page<Module> getAllModules(Pageable pageable);

	Module getModule(Long moduleId);
		
	ModuleFeature saveModuleFeature(ModuleFeature moduleFeature) throws ASBPException;

	ModuleFeature updateModuleFeature(ModuleFeature moduleFeature) throws Exception;

	void deleteModuleFeature(Long moduleFeatureId);

	Page<ModuleFeature> getAllModuleFeatures(Pageable pageable);

	ModuleFeature getModuleFeature(Long moduleFeatureId);

	Page<ModuleFeature> getModuleFeatures(Long moduleId,Pageable pageable);
	
	//module config mapping
	ModuleConfigMapping saveModuleConfigMapping(ModuleConfigMapping moduleConfigMapping) throws ASBPException;

	ModuleConfigMapping updateModuleConfigMapping(ModuleConfigMapping moduleConfigMapping) throws ASBPException;

	Page<ModuleConfigMapping> getAllModuleConfigMapping(Pageable pageable);


}
