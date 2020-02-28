package corp.asbp.platform.is;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import corp.asbp.platform.is.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableCaching
@ComponentScan({ "corp.asbp.platform.is","corp.asbp.platform.security" })
public class AsbpIsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsbpIsApplication.class, args);
	}
}
