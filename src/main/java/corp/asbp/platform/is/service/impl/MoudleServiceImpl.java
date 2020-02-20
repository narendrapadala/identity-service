package corp.asbp.platform.is.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import corp.asbp.platform.is.enumerations.CommonStatus;
import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.Client;
import corp.asbp.platform.is.model.Module;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.ModuleFeature;
import corp.asbp.platform.is.model.Role;
import corp.asbp.platform.is.repository.ClientRepository;
import corp.asbp.platform.is.repository.ModuleConfigMappingRepository;
import corp.asbp.platform.is.repository.ModuleFeatureRepository;
import corp.asbp.platform.is.repository.ModuleRepository;
import corp.asbp.platform.is.repository.RoleRepository;
import corp.asbp.platform.is.service.ModuleService;


/**
 * @author Narendra
 *
 */
@Service
@Transactional
@SuppressWarnings("unused")
public class MoudleServiceImpl implements ModuleService {

	private static Logger log = LoggerFactory.getLogger(MoudleServiceImpl.class);

	@Autowired
	private ModuleRepository moduleRepo;

	@Autowired
	private ModuleFeatureRepository moduleFeatureRepo;

	@Autowired
	private ModuleConfigMappingRepository moduleConfigMappingRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ClientRepository clientRepo;


	@Override
	public Module saveModule(Module module) throws ASBPException {
		module.setCreatedAt(System.currentTimeMillis());
		module.setCreatedBy(1L);
		module.setModifiedBy(1L);
		module.setModifiedAt(System.currentTimeMillis());
		module.setStatus(CommonStatus.ENABLED);
		// return
		return moduleRepo.save(module);
	}

	@Override
	public Module updateModule(Module module) throws Exception {
		Module m = moduleRepo.findById(module.getId())
				.orElseThrow(() -> new Exception("Module with id " + module.getId() + " doesn't exist"));

		m.setModifiedBy(1L);
		m.setModifiedAt(System.currentTimeMillis());
		// return
		return moduleRepo.save(m);
	}

	@Override
	public void deleteModule(Long moduleId) {
		// return
		moduleRepo.deleteById(moduleId);

	}

	@Override
	public Page<Module> getAllModules(Pageable pageable) {
		// return
		return moduleRepo.findAll(pageable);
	}

	@Override
	public Module getModule(Long moduleId) {
		// return
		return moduleRepo.findFirstById(moduleId);
	}

	@Override
	public ModuleFeature saveModuleFeature(ModuleFeature moduleFeature) throws ASBPException {

		Module m = moduleRepo.findById(moduleFeature.getModuleId()).orElseThrow(
				() -> new ASBPException("Module with id " + moduleFeature.getModuleId() + " doesn't exist"));

		moduleFeature.setCreatedAt(System.currentTimeMillis());
		moduleFeature.setCreatedBy(1L);
		moduleFeature.setModifiedBy(1L);
		moduleFeature.setModifiedAt(System.currentTimeMillis());
		// return
		return moduleFeatureRepo.save(moduleFeature);
	}

	@Override
	public ModuleFeature updateModuleFeature(ModuleFeature moduleFeature) throws Exception {
		Module m = moduleRepo.findById(moduleFeature.getModuleId())
				.orElseThrow(() -> new Exception("Module with id " + moduleFeature.getModuleId() + " doesn't exist"));

		ModuleFeature mf = moduleFeatureRepo.findById(moduleFeature.getId())
				.orElseThrow(() -> new Exception("ModuleFeature with id " + moduleFeature.getId() + " doesn't exist"));

		mf.setModifiedBy(1L);
		mf.setModifiedAt(System.currentTimeMillis());
		// return
		return moduleFeatureRepo.save(moduleFeature);
	}

	@Override
	public void deleteModuleFeature(Long moduleFeatureId) {
		moduleFeatureRepo.deleteById(moduleFeatureId);
	}

	@Override
	public Page<ModuleFeature> getAllModuleFeatures(Pageable pageable) {
		// return
		return moduleFeatureRepo.findAll(pageable);
	}

	@Override
	public ModuleFeature getModuleFeature(Long moduleFeatureId) {
		// return
		return moduleFeatureRepo.findFirstById(moduleFeatureId);
	}

	@Override
	public Page<ModuleFeature> getModuleFeatures(Long moduleId, Pageable pageable) {
		// return
		return moduleFeatureRepo.findAllByModuleId(moduleId, pageable);
	}

	@Override
	public ModuleConfigMapping saveModuleConfigMapping(ModuleConfigMapping moduleConfigMapping) throws ASBPException {

		Module m = moduleRepo.findById(moduleConfigMapping.getModule().getId())
				.orElseThrow(() -> new ASBPException(
						"Module with id " + moduleConfigMapping.getModule().getId() + " doesn't exist"));

		ModuleFeature mf = moduleFeatureRepo.findById(moduleConfigMapping.getModuleFeature().getId())
				.orElseThrow(() -> new ASBPException(
						"ModuleFeature with id " + moduleConfigMapping.getModuleFeature().getId() + " doesn't exist"));

		Role r = roleRepo.findById(moduleConfigMapping.getRole().getId())
				.orElseThrow(() -> new ASBPException(
						"Role with id " + moduleConfigMapping.getModule().getId() + " doesn't exist"));

		
		Client c = clientRepo.findById(moduleConfigMapping.getClient().getId())
				.orElseThrow(() -> new ASBPException(
						"Client with id " + moduleConfigMapping.getModule().getId() + " doesn't exist"));

		return moduleConfigMappingRepo.save(moduleConfigMapping);
	}

	@Override
	public ModuleConfigMapping updateModuleConfigMapping(ModuleConfigMapping moduleConfigMapping) throws ASBPException {
		Module m = moduleRepo.findById(moduleConfigMapping.getModule().getId())
				.orElseThrow(() -> new ASBPException(
						"Module with id " + moduleConfigMapping.getModule().getId() + " doesn't exist"));

		ModuleFeature mf = moduleFeatureRepo.findById(moduleConfigMapping.getModuleFeature().getId())
				.orElseThrow(() -> new ASBPException(
						"ModuleFeature with id " + moduleConfigMapping.getModuleFeature().getId() + " doesn't exist"));

		Role r = roleRepo.findById(moduleConfigMapping.getRole().getId())
				.orElseThrow(() -> new ASBPException(
						"Role with id " + moduleConfigMapping.getModule().getId() + " doesn't exist"));

		
		Client c = clientRepo.findById(moduleConfigMapping.getClient().getId())
				.orElseThrow(() -> new ASBPException(
						"Client with id " + moduleConfigMapping.getModule().getId() + " doesn't exist"));

		return moduleConfigMappingRepo.save(moduleConfigMapping);
	}

	@Override
	public Page<ModuleConfigMapping> getAllModuleConfigMapping(Pageable pageable) {
		return moduleConfigMappingRepo.findAll(pageable);
	}

}
