/**
 * 
 */
package corp.asbp.platform.is.model;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @author Narendra
 *
 */
@Entity
@Table(name="asbp_client")
public class Client {
	
	@Id
	private Long id;
	
	@Column(name="created_at")
	private Long createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Column(name="modified_at")
	private Long modifiedAt;

	@Column(name="modified_by")
	private Long modifiedBy;

	private String name;
	private String version;	
	
	@OneToMany(mappedBy="api", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<ApiModuleFeatureMapping> apiModuleFeatureMappings;



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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
