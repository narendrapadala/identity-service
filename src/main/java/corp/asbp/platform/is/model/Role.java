package corp.asbp.platform.is.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import corp.asbp.platform.is.enumerations.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="asbp_role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@AllArgsConstructor
@Builder
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Role() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_at")
	private Long createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Column(name="description")
	private String description;	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="category_id",insertable=true,updatable=true)
	private RoleCategory roleCategory;
	
	@Column(name="modified_at")
	private Long modifiedAt;

	@Column(name="modified_by")
	private Long modifiedBy;

	@Column(name="name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private CommonStatus status;
	
	@Column(name="priority")
	private Double priority;
	
	@JsonIgnore
	@OneToMany(mappedBy="role")
	private List<ModuleConfigMapping> moduleConfigMappings;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public RoleCategory getRoleCategory() {
		return roleCategory;
	}

	public void setRoleCategory(RoleCategory roleCategory) {
		this.roleCategory = roleCategory;
	}

	public List<ModuleConfigMapping> getModuleConfigMappings() {
		return moduleConfigMappings;
	}

	public void setModuleConfigMappings(List<ModuleConfigMapping> moduleConfigMappings) {
		this.moduleConfigMappings = moduleConfigMappings;
	}
}