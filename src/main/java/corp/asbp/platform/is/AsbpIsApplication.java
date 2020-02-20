package corp.asbp.platform.is;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import corp.asbp.platform.is.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableCaching
public class AsbpIsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsbpIsApplication.class, args);
	}
}
