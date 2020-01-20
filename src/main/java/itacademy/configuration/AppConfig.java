package itacademy.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The type App config.
 *
 * Import another configuration files.
 */
@Configuration
@ComponentScan(basePackages = "itacademy")
@Import({WebConfig.class, DatabaseConfig.class, MessageConfiguration.class, SecurityConfiguration.class})
public class AppConfig {
}
