package corp.asbp.platform.is.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import corp.asbp.platform.is.enumerations.CommonStatus;
import lombok.Data;


/**
 * The persistent class for the user_role_mapping database table.
 * 
 */
@Data
@Entity
@Table(name="asbp_user_role_mapping")
@NamedQuery(name="UserRoleMapping.findAll", query="SELECT u FROM UserRoleMapping u")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserRoleMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRoleMappingPK id;

	@Column(name="created_at")
	private Long createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Column(name="modified_at")
	private Long modifiedAt;

	@Column(name="modified_by")
	private Long modifiedBy;


	@Enumerated(EnumType.STRING)
	private CommonStatus status;

	public UserRoleMapping() {
	}
	
	public UserRoleMapping(User user, Role role) {
		setId(new UserRoleMappingPK(user,role));
		setStatus(CommonStatus.ENABLED);
		auditCreation(user.getId());
	}
	
	public void auditCreation(Long userId) {
		this.createdAt = System.currentTimeMillis();
		this.modifiedAt = System.currentTimeMillis();
		this.createdBy = userId;
		this.modifiedBy = userId;
	}
	
	public UserRoleMappingPK getId() {
		return this.id;
	}

	public void setId(UserRoleMappingPK id) {
		this.id = id;
	}

	public Long getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedAt() {
		return this.modifiedAt;
	}

	public void setModifiedAt(Long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Long getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}