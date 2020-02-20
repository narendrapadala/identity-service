/**
 * 
 */
package corp.asbp.platform.is.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Narendra
 *
 */
@Entity
@Table(name = "asbp_module_feature")
public class ModuleFeature implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "module_id")
	private Long moduleId;

	private String key;

	private String name;

	private String description;

	@Column(name = "created_at")
	private Long createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "modified_at")
	private Long modifiedAt;

	@Column(name = "modified_by")
	private Long modifiedBy;
	
	@JsonIgnore
	@OneToMany(mappedBy="moduleFeature",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<ApiModuleFeatureMapping> apiModuleFeatureMappings;
	
	@JsonIgnore
	@OneToMany(mappedBy="moduleFeature", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<ModuleConfigMapping> moduleConfigMappings;

	public Set<ApiModuleFeatureMapping> getApiModuleFeatureMappings() {
		return apiModuleFeatureMappings;
	}

	public void setApiModuleFeatureMappings(Set<ApiModuleFeatureMapping> apiModuleFeatureMappings) {
		this.apiModuleFeatureMappings = apiModuleFeatureMappings;
	}
	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public List<ModuleConfigMapping> getModuleConfigMappings() {
		return moduleConfigMappings;
	}

	public void setModuleConfigMappings(List<ModuleConfigMapping> moduleConfigMappings) {
		this.moduleConfigMappings = moduleConfigMappings;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
