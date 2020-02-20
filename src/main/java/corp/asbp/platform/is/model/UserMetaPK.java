package corp.asbp.platform.is.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the api_module_feature_mapping database table.
 * 
 */
@Embeddable
public class UserMetaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false)
	private Long userId;

	@Column(name="key", insertable=false, updatable=false)
	private String key;
	
	public UserMetaPK(Long userId, String key) {
		super();
		this.userId = userId;
		this.key = key;
	}
	
	public UserMetaPK() {
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserMetaPK)) {
			return false;
		}
		UserMetaPK castOther = (UserMetaPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.key.equals(castOther.key);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.key.hashCode();
		
		return hash;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}