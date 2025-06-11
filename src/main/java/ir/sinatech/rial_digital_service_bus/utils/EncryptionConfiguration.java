package ir.sinatech.rial_digital_service_bus.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "encryption")
public class EncryptionConfiguration {
    // Getters and setters
    private String key;
    private String iv;

}
