package corp.asbp.platform.is.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the api_module_feature_mapping database table.
 * 
 */
@Embeddable
public class ModuleModuleFeaturePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="api_id", insertable=false, updatable=false)
	private Long apiId;

	@Column(name="module_feature_id", insertable=false, updatable=false)
	private Long moduleFeatureId;
	
	public ModuleModuleFeaturePK(Long apiId, Long moduleFeatureId) {
		super();
		this.apiId = apiId;
		this.moduleFeatureId = moduleFeatureId;
	}
	
	public ModuleModuleFeaturePK() {
	}
	public Long getApiId() {
		return this.apiId;
	}
	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}


	public Long getModuleFeatureId() {
		return moduleFeatureId;
	}

	public void setModuleFeatureId(Long moduleFeatureId) {
		this.moduleFeatureId = moduleFeatureId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ModuleModuleFeaturePK)) {
			return false;
		}
		ModuleModuleFeaturePK castOther = (ModuleModuleFeaturePK)other;
		return 
			this.apiId.equals(castOther.apiId)
			&& this.moduleFeatureId.equals(castOther.moduleFeatureId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.apiId.hashCode();
		hash = hash * prime + this.moduleFeatureId.hashCode();
		
		return hash;
	}
}