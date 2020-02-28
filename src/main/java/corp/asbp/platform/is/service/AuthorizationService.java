/**
 * 
 */
package corp.asbp.platform.is.service;

import java.io.IOException;

import org.springframework.http.HttpMethod;

import corp.asbp.platform.is.dto.GuestApi;
import corp.asbp.platform.is.dto.SsHeader;
import corp.asbp.platform.is.dto.UsersProfileDto;
import corp.asbp.platform.is.exception.AccessForbiddenException;
import corp.asbp.platform.is.exception.ClientVersionUpgradeException;
import corp.asbp.platform.is.exception.ResourceNotFoundException;
import corp.asbp.platform.is.exception.UnAuthorizedException;


/**
 * @author Narendra
 *
 */
public interface AuthorizationService {
	
	public UsersProfileDto validateUser(SsHeader ssHeader, String uri, HttpMethod reqMethod) throws AccessForbiddenException, UnAuthorizedException,
																ResourceNotFoundException, IOException, ClientVersionUpgradeException;

	
	public UsersProfileDto getUserFromSession(String sessionId) throws UnAuthorizedException, IOException;
	
	public GuestApi getGuestApisFromSession(String sessionId);

}
