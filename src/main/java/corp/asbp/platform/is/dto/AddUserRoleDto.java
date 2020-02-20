package corp.asbp.platform.is.dto;

import java.util.List;

import lombok.Data;

@Data
public class AddUserRoleDto {

	private Long userId;
	
	private List<Long> rolesIds;
}
