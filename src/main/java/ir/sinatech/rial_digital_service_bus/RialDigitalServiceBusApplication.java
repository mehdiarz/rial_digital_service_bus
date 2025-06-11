package ir.sinatech.rial_digital_service_bus;

import ir.sinatech.rial_digital_service_bus.utils.EncryptionConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EncryptionConfiguration.class)

public class RialDigitalServiceBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(RialDigitalServiceBusApplication.class, args);
    }

}
