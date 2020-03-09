package corp.asbp.platform.is.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;


/**
 * The persistent class for the module_config_mapping database table.
 * 
 */
@Entity
@Table(name="asbp_module_config_mapping")
@NamedQuery(name="ModuleConfigMapping.findAll", query="SELECT m FROM ModuleConfigMapping m")
@AllArgsConstructor
@Builder
public class ModuleConfigMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	public ModuleConfigMapping() {
		
	}
	
	@EmbeddedId
	private ModuleConfigMappingPK id;
	
	public ModuleConfigMappingPK getId() {
		return id;
	}

	public void setId(ModuleConfigMappingPK id) {
		this.id = id;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public ModuleFeature getModuleFeature() {
		return moduleFeature;
	}

	public void setModuleFeature(ModuleFeature moduleFeature) {
		this.moduleFeature = moduleFeature;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="module_id",insertable=false,updatable=false)
	private Module module;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="module_feature_id",insertable=false,updatable=false)
	private ModuleFeature moduleFeature;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="role_id",insertable=false,updatable=false)
	private Role role;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="client_id",insertable=false,updatable=false)
	private Client client;
	
}