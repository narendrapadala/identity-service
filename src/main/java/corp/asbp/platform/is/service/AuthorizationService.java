/**
 * 
 */
package corp.asbp.platform.is.service;

import java.io.IOException;

import org.springframework.http.HttpMethod;

import corp.asbp.platform.is.dto.SsHeader;
import corp.asbp.platform.is.dto.UserResponseDto;
import corp.asbp.platform.is.exception.AccessForbiddenException;
import corp.asbp.platform.is.exception.ClientVersionUpgradeException;
import corp.asbp.platform.is.exception.ResourceNotFoundException;
import corp.asbp.platform.is.exception.UnAuthorizedException;


/**
 * @author Narendra
 *
 */
public interface AuthorizationService {
	
	public UserResponseDto validateUser(SsHeader ssHeader, String uri, HttpMethod reqMethod) throws AccessForbiddenException, UnAuthorizedException,
																ResourceNotFoundException, IOException, ClientVersionUpgradeException;

	
	public UserResponseDto getUserFromSession(String sessionId) throws UnAuthorizedException, IOException;

}
