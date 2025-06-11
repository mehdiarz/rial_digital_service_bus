package ir.sinatech.rial_digital_service_bus.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    // Getters and setters
    private String baseUrl;
    private String adminToken;
    private String adminUser;
    private String branch;

}
