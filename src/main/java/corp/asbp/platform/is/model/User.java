package corp.asbp.platform.is.model;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;

import corp.asbp.platform.is.enumerations.AuthProvider;
import corp.asbp.platform.is.enumerations.CommonStatus;
import corp.asbp.platform.is.util.PostgreSQLEnumType;
import lombok.Data;

@Entity
@Table(name = "asbp_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@TypeDef(name = "pGEnumUserStatusUserType", typeClass = PostgreSQLEnumType.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name="user_group_id")
	private Long userGroupId;
	
	@Column(name="parent_user_id")
	private Long parentUserId;
	
	@Column(name="customer_company_name")
	private String customerCompanyName;

    @Email
    @Column(nullable = false)
    private String email;
    
    @JsonIgnore
    private String password;
    
	@Column(name="is_active")
	private String isActive;
	
	@Column(name="account_verified")
	private String accountVerified;
	
	@Column(name="account_type")
	private String accountType;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="address_line1")
	private String addressLine1;
	
	@Column(name="address_line2")
	private String addressLine2;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zip")
	private String zip;
	
	@Column(name="country")
	private String country;
	
	@Column(name="credit_limit")
	private BigDecimal creditLimit;

	@Column(name="phone_no")
	private String phoneNo;

	@Column(name="created_at")
	private Long createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Column(name="modified_at")
	private Long modifiedAt;

	@Column(name="modified_by")
	private Long modifiedBy;

    @Column(nullable = false)
    private String name;   
    
    @Column(name="image_url")
    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name="provider_id")
    private String providerId;
    
	@Enumerated(EnumType.STRING)
	@Type(type = "pGEnumUserStatusUserType")
	private CommonStatus status;
	
	public  User() {
		
	}
	


}
