package corp.asbp.platform.is.model;

import java.io.Serializable;
import java.util.Set;

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

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.Type;
import org.springframework.http.HttpMethod;

import corp.asbp.platform.is.enumerations.CommonStatus;
import corp.asbp.platform.is.util.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the api database table.
 * 
 */
@Entity
@Table(name = "asbp_api")
@NamedQuery(name = "Api.findAll", query = "SELECT a FROM Api a")
@AllArgsConstructor
@Builder
@TypeDefs({ @TypeDef(name = "pGEnumTypeUserType", typeClass = PostgreSQLEnumType.class),
		@TypeDef(name = "pGEnumStatusUserType", typeClass = PostgreSQLEnumType.class) })

public class Api implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_at")
	private Long createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	private String description;

	@Column(name = "modified_at")
	private Long modifiedAt;

	@Column(name = "modified_by")
	private Long modifiedBy;

	private String name;

	@Enumerated(EnumType.STRING)
	@Type(type = "pGEnumStatusUserType")
	private CommonStatus status;

	private String version;

	@Enumerated(EnumType.STRING)
	@Type(type = "pGEnumTypeUserType")
	private HttpMethod type;

	@OneToMany(mappedBy = "api", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ApiModuleFeatureMapping> apiModuleFeatureMappings;

	public Api() {
	}

	public Api(Long createdBy) {
		this.createdAt = System.currentTimeMillis();
		this.modifiedAt = System.currentTimeMillis();
		this.createdBy = createdBy;
		this.modifiedBy = createdBy;
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

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<ApiModuleFeatureMapping> getApiModuleFeatureMappings() {
		return apiModuleFeatureMappings;
	}

	public void setApiModuleFeatureMappings(Set<ApiModuleFeatureMapping> apiModuleFeatureMappings) {
		this.apiModuleFeatureMappings = apiModuleFeatureMappings;
	}

	public HttpMethod getType() {
		return type;
	}

	public void setType(HttpMethod type) {
		this.type = type;
	}

}