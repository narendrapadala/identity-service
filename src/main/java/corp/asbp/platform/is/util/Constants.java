
package corp.asbp.platform.is.util;

import org.springframework.http.HttpMethod;

public class Constants {


	public static final String DECRYPTION_API_ENDPOINT ="DECRYPTION_API_ENDPOINT";

	public static final String DECRYPTION_API_KEY ="DECRYPTION_API_KEY";

	public static final String DECRYPTION_API_RESPONSE_FORMAT ="DECRYPTION_API_RESPONSE_FORMAT";

	public static final String DECRYPTION_API_RESPONSE_MAPPING ="DECRYPTION_API_RESPONSE_MAPPING";
	
	public static final String VENDOR_LOGIN_TYPE ="VENDOR_LOGIN_TYPE";
	
	
	
	
	public static final String SESSION_INFO ="sessionInfo";

	public static final String BOOKING ="booking";

	public static final String ENTITY ="entity";

	public static final String UNDERSCORE ="_";

	public static final String SUCCESS = "success";

	public static final String SESSION_ID ="session_id";

	public static final int SESSION_INVALID_CODE = 401;

	public static final String REDIS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";

	public static final String GOOGLE_CALENDER_DATE_FORMAT ="yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static final String MEETING_REMINDER = "meeting_reminder";

	public static final String USER_REMINDER = "user_reminder";

	public static final String LOGIN_MESSAGE = "Successfully Logged In !!";

	public static final String LOGIN_FAILURE = "Invalid Credentials !!";
	
	public static final String SIGNUP_API = "/asbp/user/register";
	
	public static final HttpMethod SIGNUP_API_METHOD = HttpMethod.POST;
	
	public static final String ADMIN_ROLE_NAME = "ADMIN";
	
	public static final String SUPER_ADMIN_ROLE_NAME = "SUPER_ADMIN";
	
	public static final String CORPORATE_ROLE_NAME = "CORPORATE_USER";
	
	public static final String ANY_USER_ROLE_NAME = "ANY_USER";
	
	public static final String SIGNUP_MODULE = "SIGNUP";
	
	public static final String FORGOT_PWD_MODULE = "FORGOT_PASSWORD";
	
	public static final Long SUPER_ADMIN_ID = 1L;
	
	public static final String MODULE_FEATURE_HEADER = "feature_key";

	public static final String OAUTH_LOGIN_TYPE = "ONEDRIVE";

	public static final String ONEDRIVE_REFRESH_GRANT = "refresh_token";

	public static final String ONEDRIVE_AUTH_GRANT = "authorization_code";
	
	public static final String BULK_USERS_HEADERS = "Employee Code,Email Id,First Name,Last Name,Mobile,Tenant Name";

	public static final String BULK_APIS_HEADERS = "Api-name,method,description,roles";

	public static final Long DEFAULT_ZONE_ID = 5L;
	
	public static final String FILE_BASED ="FILE";
	
	public static final String DB_BASED ="DB";
	
	public static final String ACTIVE_DIRECTORY_BASED ="AD";

	public static final String DEFAULT_STATUS_NAME = "Available";
	
	public static final String SAML_BASED = "SAML";

	public static final String SESSION_COOKIE_NAME = "SESSION_COOKIE";

	public static final String MODULE_NAME_KEY = "moduleName";

	public static final String EXTERNAL_AUTHENTICATION = "EXTERNAL";

	public static final Long DEFAULT_GUEST_ROLE_ID = 4L;
	
	public static final String GUEST_ROLE_NAME = "GUEST";

	public static final Long APP_CLIENT_ID = 1L;

	public static final String DASHBOARD_CLIENT_KEY = "ADMIN_WEB_APP";
	
	public static final String MOBILE_APP_CLIENT_KEY = "END_USER_MOBILE_APP";

	public static final Long DASHBOARD_CLIENT_ID = 4L;

	public static final String BULK_ZONES_HEADERS = "Company,Domain,Region,Country,State,City,Location,Building,Floor,SecureID";

	public static final String CORPORATE_OTP_KEY = "CORPORATE_EMAIL_VALIDATION";

	public static final String OAUTH_BASED = "OAUTH";
	
	public static final Long MODULE_ID =22l;
	
	public static final String MOBILE_OTP_KEY ="MOBILE NUMBER VERIFICATION";
	
	public static final String OTP_TEMPLATE_ID ="OTP_TEMPLATE";
	

	
}
