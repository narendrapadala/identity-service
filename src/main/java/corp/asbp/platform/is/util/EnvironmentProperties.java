/**
 * 
 */
package corp.asbp.platform.is.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * @author Narendra
 *
 */
@Component
@Getter
public class EnvironmentProperties {
	
	@Value("${redis.hostname:}")
	private String redisHost;

	@Value("${redis.port:}")
	private String redisPort;
	
	@Value("${server.servlet.session.timeout:0}")
	private int sessionTimeout;
	
	@Value("${asbp.redis.config.cluster.enabled:false}")
	private boolean redisClusterEnabled;

	@Value("${asbp.redis.password:}")
	private String redisPassword;
	
	@Value("${asbp.guest.role:GUEST}")
	private String guestRole;

	
	
}
