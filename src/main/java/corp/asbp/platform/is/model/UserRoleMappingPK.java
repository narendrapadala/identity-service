package corp.asbp.platform.is.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

/**
 * The primary key class for the user_role_mapping database table.
 * 
 */
@Embeddable
@AllArgsConstructor
public class UserRoleMappingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="role_id", insertable=false, updatable=false)
	private Long roleId;

	@Column(name="user_id", insertable=false, updatable=false)
	private Long userId;

	public UserRoleMappingPK() {
	}
	public UserRoleMappingPK(User user, Role role) {
			
		setUserId(user.getId());
		setRoleId(role.getId());
	}
	public Long getRoleId() {
		return this.roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getUserId() {
		return this.userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserRoleMappingPK)) {
			return false;
		}
		UserRoleMappingPK castOther = (UserRoleMappingPK)other;
		return 
			this.roleId.equals(castOther.roleId)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}