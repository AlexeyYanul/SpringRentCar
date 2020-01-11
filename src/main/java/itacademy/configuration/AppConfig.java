package itacademy.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "itacademy")
@Import({WebConfig.class, DatabaseConfig.class, MessageConfiguration.class})
public class AppConfig {
}
