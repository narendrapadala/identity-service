package corp.asbp.platform.is.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * The persistent class for the user database table.
 * 
 */
@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name="asbp_user_meta")
@JsonIgnoreProperties(ignoreUnknown=true)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserMeta implements Serializable {
	private static final long serialVersionUID = 1L;

	public UserMeta () {
		
	}
	
	@EmbeddedId
	private UserMetaPK id;

	private String value;
	
	@Column(name="created_at")
	private Long createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Column(name="modified_at")
	private Long modifiedAt;

	@Column(name="modified_by")
	private Long modifiedBy;

}