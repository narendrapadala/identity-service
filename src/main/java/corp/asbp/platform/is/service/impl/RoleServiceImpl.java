package corp.asbp.platform.is.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

//import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import corp.asbp.platform.is.dto.AssignRolesAndResponsibilitesDto;
import corp.asbp.platform.is.dto.RoleAndResponsibilityMappingDto;
import corp.asbp.platform.is.enumerations.CommonStatus;
import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.Client;
import corp.asbp.platform.is.model.Module;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.ModuleFeature;
import corp.asbp.platform.is.model.Role;
import corp.asbp.platform.is.model.RoleCategory;
import corp.asbp.platform.is.repository.ClientRepository;
import corp.asbp.platform.is.repository.ModuleConfigMappingRepository;
import corp.asbp.platform.is.repository.ModuleFeatureRepository;
import corp.asbp.platform.is.repository.ModuleRepository;
import corp.asbp.platform.is.repository.RoleCategoryRepository;
import corp.asbp.platform.is.repository.RoleRepository;
import corp.asbp.platform.is.service.RolesService;



/**
 * @author Narendra
 *
 */
@Service
@Transactional
@SuppressWarnings("unused")
public class RoleServiceImpl implements RolesService {

	private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private RoleCategoryRepository roleCategoryRepo;

	@Autowired
	private ModuleRepository moduleRepo;

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private ModuleFeatureRepository moduleFeatureRepo;

	@Autowired
	private ModuleConfigMappingRepository moduleConfigMappingRepo;

	@Override
	public Role saveRole(Role api) throws ASBPException {

		RoleCategory role = roleCategoryRepo.findById(api.getRoleCategory().getId()).orElseThrow(
				() -> new ASBPException("role category with id " + api.getRoleCategory() + " doesn't exist"));

		api.setCreatedAt(System.currentTimeMillis());
		api.setCreatedBy(1L);
		api.setModifiedBy(1L);
		api.setModifiedAt(System.currentTimeMillis());
		api.setStatus(CommonStatus.ENABLED);
		return roleRepo.save(api);
	}

	@Override
	public Role updateRole(Role api) throws Exception {
		Role ex_api = roleRepo.findById(api.getId())
				.orElseThrow(() -> new Exception("role with id " + api.getId() + " doesn't exist"));

		RoleCategory role = roleCategoryRepo.findById(api.getRoleCategory().getId())
				.orElseThrow(() -> new Exception("role category with id " + api.getRoleCategory() + " doesn't exist"));

		ex_api.setModifiedBy(1L);
		ex_api.setModifiedAt(System.currentTimeMillis());
		return roleRepo.save(ex_api);
	}

	@Override
	public void deleteRole(Long roleId) {
		roleRepo.deleteById(roleId);

	}

	@Override
	public Page<Role> getAllRoles(String searchColumn, String searchValue, Pageable pageable) {
		//if (StringUtils.isEmpty(searchColumn)) {
			//return roleRepo.findAll(pageable);
		//} else {
			// Specification.where(getFilterQuery(searchColumn, searchValue)),
			return roleRepo.findAll(pageable);
		//}
	}

	@Override
	public Role getRole(Long roleId) {
		return roleRepo.findFirstById(roleId);
	}

	@Override
	public Page<RoleCategory> getAllRoleCategories(Pageable pageable) {
		return roleCategoryRepo.findAll(pageable);
	}

	@Override
	public RoleCategory saveRoleCategory(RoleCategory roleCategory) throws ASBPException {
		return roleCategoryRepo.save(roleCategory);
	}

	@Override
	public RoleCategory updateRoleCategory(RoleCategory roleCategory) throws Exception {
		RoleCategory roleCat = roleCategoryRepo.findById(roleCategory.getId())
				.orElseThrow(() -> new Exception("Role category with id " + roleCategory.getId() + " doesn't exist"));

		roleCat.setModifiedBy(1L);
		roleCat.setModifiedAt(System.currentTimeMillis());
		return roleCategoryRepo.save(roleCat);

	}

	@Override
	public void deleteRoleCategory(Long roleCatId) {
		roleCategoryRepo.deleteById(roleCatId);
	}

	@Override
	public RoleCategory getRoleCategory(Long roleCatId) {
		// return
		return roleCategoryRepo.findFirstById(roleCatId);
	}

	@Override
	public List<ModuleConfigMapping> getUserRolesModules(Long roleId, Long clientId) {
		return null;
	}

	@Override
	public RoleAndResponsibilityMappingDto getRoleAndResponsibilities() {
		// list all roles
		List<RoleCategory> listRc = roleCategoryRepo.findAll();

		RoleAndResponsibilityMappingDto res = new RoleAndResponsibilityMappingDto();

		List<RoleAndResponsibilityMappingDto.RoleAndResponsibilities> rrList = new ArrayList<>();

		List<RoleAndResponsibilityMappingDto.ModuleAndFeatures> mfList = new ArrayList<>();

		List<Module> listModule = moduleRepo.findAll();

		// loop role categories
		for (Module module : listModule) {
			RoleAndResponsibilityMappingDto.ModuleAndFeatures maf = new RoleAndResponsibilityMappingDto.ModuleAndFeatures();
			maf.setModule(module);

			List<ModuleFeature> mFeture = moduleFeatureRepo.findAllByModuleId(module.getId());
			maf.setModuleFeatures(mFeture);

			mfList.add(maf);

		}

		// loop role categories
		for (RoleCategory rc : listRc) {

			RoleAndResponsibilityMappingDto.RoleAndResponsibilities rr = new RoleAndResponsibilityMappingDto.RoleAndResponsibilities();
			rr.setRoleCategory(rc);

			List<Role> roles = roleRepo.findAllByRoleCategory(rc);
			rr.setRoles(roles);

			List<Long> roleIds = new ArrayList<Long>();
			for (Role role : roles) {
				roleIds.add(role.getId());
			}
			// check
			if (roleIds.size() > 0) {
				List<ModuleConfigMapping> mcp = moduleConfigMappingRepo.findByRoleIdIn(roleIds);
				rr.setModules(mcp);
			}

			rrList.add(rr);
		}
		res.setAllModuleAndFeatures(mfList);
		// set
		res.setRoleAndResponsibilities(rrList);

		return res;

	}

	@Override
	public List<ModuleConfigMapping> addRolesAndResponsibilities(AssignRolesAndResponsibilitesDto assinRoles)
			throws ASBPException {
		
		Long roleId = assinRoles.getRoleId() ;
		// check
		if (roleId > 0) {
			Role role = roleRepo.findById(assinRoles.getRoleId())
					.orElseThrow(() -> new ASBPException("Role with id " + roleId + " doesn't exist"));

			Client client = clientRepo.findById(assinRoles.getClientId()).orElseThrow(
					() -> new ASBPException("Client with id " + assinRoles.getClientId() + " doesn't exist"));

			List<ModuleConfigMapping> mcm = moduleConfigMappingRepo
					.findAllByIdRoleIdAndIdClientId(assinRoles.getRoleId(), assinRoles.getClientId());
			// check
			if (mcm != null) {
				// remove old roles and responsibilities
				moduleConfigMappingRepo.deleteByIdRoleIdAndIdClientId(assinRoles.getRoleId(), assinRoles.getClientId());
			}
			List<ModuleConfigMapping> mcfList =  new ArrayList<>();
			// check
			if (assinRoles.getAssingRoles().size() > 0) {
				// loop
				for (AssignRolesAndResponsibilitesDto.RoleAndResponsibilities assingRole : assinRoles
						.getAssingRoles()) {

					// check
					if (assingRole.getModuleId() > 0) {
						

						Module module = moduleRepo.findFirstById(assingRole.getModuleId());
						// check
						if (module != null) {
							// check
							if (assingRole.getFeatureIds().size() > 0) {
								System.out.println("Role Id" + roleId);
								System.out.println("Client Id" + assinRoles.getClientId());
								System.out.println("Module Id" + assingRole.getModuleId());

								// loop
								for (Long featureId : assingRole.getFeatureIds()) {
									
									//check
									if(featureId > 0) {
										//check
										ModuleFeature moduleFeature = moduleFeatureRepo.findFirstById(featureId);
										//check
										if(moduleFeature!=null) {
											//set
											Role mRole = new Role();
											mRole.setId(roleId);
											//set
											Client mClient = new Client();
											mClient.setId(assinRoles.getClientId());
											//set
											ModuleFeature mFeature = new ModuleFeature();
											mFeature.setId(featureId);
											//set
											Module mModule = new Module();
											mModule.setId(assingRole.getModuleId());
											//set
											ModuleConfigMapping moduleConfigMapping = new ModuleConfigMapping();
											moduleConfigMapping.setRole(mRole);
											moduleConfigMapping.setClient(mClient);
											moduleConfigMapping.setModule(mModule);
											moduleConfigMapping.setModuleFeature(mFeature);
											
											ModuleConfigMapping mcmSave = moduleConfigMappingRepo.save(moduleConfigMapping);
											//set
											mcfList.add(mcmSave);											
										}
										
									}
								}

							}

						}

					}

				}

			}

		}
		return null;
	}

}
