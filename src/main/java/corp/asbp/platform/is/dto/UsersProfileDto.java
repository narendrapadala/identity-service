package corp.asbp.platform.is.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class UsersProfileDto {

	private User user;
	private List<String> roles;
	private List<String> modules;
	private List<String> features;

	// private List<Role> roles;

	// private List<Module> modules;

	// private List<ModuleFeature> moduleFeatures;

	// private List<ModuleConfigMapping> moduleConfigMapping;

	@Data
	public static class User {

		private Long id;
		private Long userGroupId;
		private Long parentUserId;
		private String customerCompanyName;
		private String email;
		private String accountType;
		private String firstName;
		private String lastName;
		private String addressLine2;
		private String addressLine1;
		private String state;
		private String city;
		private String zip;
		private String country;
		private BigDecimal creditLimit;
		private String phoneNo;
	}

}
