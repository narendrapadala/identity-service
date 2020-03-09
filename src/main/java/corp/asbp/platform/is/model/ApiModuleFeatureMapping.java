package corp.asbp.platform.is.model;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name="asbp_api_module_feature_mapping")
@NamedQuery(name="ApiModuleFeatureMapping.findAll", query="SELECT a FROM ApiModuleFeatureMapping a")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@AllArgsConstructor
@Builder
public class ApiModuleFeatureMapping {
	
	@EmbeddedId
	private ApiModuleFeatureMappingPK id;

	@Column(name="created_at")
	private Long createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Column(name="modified_at")
	private Long modifiedAt;

	@Column(name="modified_by")
	private Long modifiedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="api_id",insertable=false,updatable=false)
	private Api api;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="module_feature_id",insertable=false,updatable=false)
	private ModuleFeature moduleFeature;
	
	public ApiModuleFeatureMapping() {
	}
	
	public ApiModuleFeatureMapping(Api api,ModuleFeature moduleFeature,Long createdBy) {
		setId(new ApiModuleFeatureMappingPK(api.getId(),moduleFeature.getId()));
		auditCreation(createdBy);
	}
	
	public void auditCreation(Long createdBy) {
		this.createdAt = System.currentTimeMillis();
		this.modifiedAt = System.currentTimeMillis();
		this.createdBy = createdBy;
		this.modifiedBy = createdBy;
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

	public ApiModuleFeatureMappingPK getId() {
		return id;
	}

	public void setId(ApiModuleFeatureMappingPK id) {
		this.id = id;
	}

	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	public ModuleFeature getModuleFeature() {
		return moduleFeature;
	}

	public void setModuleFeature(ModuleFeature moduleFeature) {
		this.moduleFeature = moduleFeature;
	}


}
