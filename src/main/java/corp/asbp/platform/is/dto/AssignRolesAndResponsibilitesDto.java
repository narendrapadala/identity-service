package corp.asbp.platform.is.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRolesAndResponsibilitesDto {

	private Long roleId;
	
	private Long clientId;
	
	private List<RoleAndResponsibilities> assingRoles;
	
	@Data
    @NoArgsConstructor
    public static class RoleAndResponsibilities 
    {
		private Long moduleId;
		
		private List<Long> featureIds;
    }
}
