package corp.asbp.platform.is.dto;

import java.util.List;

import corp.asbp.platform.is.model.Module;
import corp.asbp.platform.is.model.ModuleConfigMapping;
import corp.asbp.platform.is.model.ModuleFeature;
import corp.asbp.platform.is.model.Role;
import corp.asbp.platform.is.model.RoleCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleAndResponsibilityMappingDto {

    private List<RoleAndResponsibilities> roleAndResponsibilities;

    private List<ModuleAndFeatures> allModuleAndFeatures;

    
	@Data
    @NoArgsConstructor
    public static class RoleAndResponsibilities 
    {
		private RoleCategory roleCategory;
		
		private List<Role> roles;
		
		List<ModuleConfigMapping> modules;
    }
	
	@Data
    @NoArgsConstructor
    public static class ModuleAndFeatures
    {
		private Module module;
		
		private List<ModuleFeature> moduleFeatures;
    }
}
