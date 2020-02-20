package corp.asbp.platform.is.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import corp.asbp.platform.is.enumerations.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;


/**
 * The persistent class for the module database table.
 * 
 */
@Entity
@Table(name="asbp_module")
@NamedQuery(name="Module.findAll", query="SELECT m FROM Module m")
@AllArgsConstructor
@Builder
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator="ModuleGenerator")
	@GenericGenerator(name="ModuleGenerator", strategy="corp.asbp.platform.is.util.ModuleSequenceGenerator")
	private Long id;

	@Column(name="created_at")
	private Long createdAt;

	@Column(name="created_by")
	private Long createdBy;

	private String description;

	@Column(name="modified_at")
	private Long modifiedAt;

	@Column(name="modified_by")
	private Long modifiedBy;

	private String name;
	
	@Enumerated(EnumType.STRING)
	private CommonStatus status;
	
	@Column(name="display_order")
	private Integer displayOrder;

	@OneToMany(mappedBy="module", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<ModuleConfigMapping> moduleConfigMappings;
	
	public Module() {
	}
	
	public void auditCreation(Long userId) {
		this.createdAt = System.currentTimeMillis();
		this.modifiedAt = System.currentTimeMillis();
		this.createdBy = userId;
		this.modifiedBy = userId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonStatus getStatus() {
		return this.status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}



}