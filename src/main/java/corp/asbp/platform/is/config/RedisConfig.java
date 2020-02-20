package corp.asbp.platform.is.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.stereotype.Component;

import corp.asbp.platform.is.util.EnvironmentProperties;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisConfig {

	Logger log = LoggerFactory.getLogger(RedisConfig.class);
	
	@Autowired
	private EnvironmentProperties properties;
	
	@Bean
	 ValueOperations<String, String> redisOperations(@Autowired JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate.opsForValue();
	}
	
	
	@Bean
	JedisConnectionFactory getConnection() {
		if(!properties.isRedisClusterEnabled()) {
			log.info("Starting redis in  standalone mode...");
			RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
			redisConfig.setHostName(properties.getRedisHost());
			redisConfig.setPassword(RedisPassword.of(properties.getRedisPassword()));
			redisConfig.setPort(Integer.parseInt(properties.getRedisPort()));
			JedisClientConfiguration configuration = JedisClientConfiguration.builder().usePooling().
														poolConfig(new JedisPoolConfig()).build();
			return new JedisConnectionFactory(redisConfig,configuration);
		}
		log.info("Starting redis in cluster mode...");
		List<RedisNode> redisNodes = new ArrayList<>();
		String[] redisIps = properties.getRedisHost().split(",");
		String[] redisPorts = properties.getRedisPort().split(",");
		for (int i = 0; i < redisIps.length; i++) {
			redisNodes.add(new RedisNode(redisIps[i], Integer.parseInt(redisPorts[i])));
		}
		RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
		redisConfig.setClusterNodes(redisNodes);
		return new JedisConnectionFactory(redisConfig);
	}
	
	@Bean
	public static ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}

}
