package corp.asbp.platform.is.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the module_config_mapping database table.
 * 
 */
@Embeddable
public class ModuleConfigMappingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="module_feature_id", insertable=false, updatable=false)
	private Long moduleFeatureId;

	@Column(name="role_id", insertable=false, updatable=false)
	private Long roleId;
	
	@Column(name="client_id", insertable=false, updatable=false)
	private Long clientId;

	@Column(name="module_id", insertable=false, updatable=false)
	private Long moduleId;

	public ModuleConfigMappingPK() {
	}
	
	public ModuleConfigMappingPK(Long moduleFeatureId, Long roleId, Long clientId, Long moduleId) {
		super();
		this.moduleFeatureId = moduleFeatureId;
		this.roleId = roleId;
		this.clientId = clientId;
		this.moduleId = moduleId;
	}


	public Long getRoleId() {
		return this.roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getModuleId() {
		return this.moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ModuleConfigMappingPK)) {
			return false;
		}
		ModuleConfigMappingPK castOther = (ModuleConfigMappingPK)other;
		return 
			this.moduleFeatureId.equals(castOther.moduleFeatureId)
			&& this.roleId.equals(castOther.roleId)
			&& this.moduleId.equals(castOther.moduleId);
	}

	public Long getModuleFeatureId() {
		return moduleFeatureId;
	}

	public void setModuleFeatureId(Long moduleFeatureId) {
		this.moduleFeatureId = moduleFeatureId;
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.moduleFeatureId.hashCode();
		hash = hash * prime + this.roleId.hashCode();
		hash = hash * prime + this.moduleId.hashCode();
		
		return hash;
	}
}