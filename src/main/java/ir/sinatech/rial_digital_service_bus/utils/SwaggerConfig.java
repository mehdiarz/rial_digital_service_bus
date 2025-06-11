package ir.sinatech.rial_digital_service_bus.utils;

import ir.sinatech.rial_digital_service_bus.model.ApiVersion;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("v1")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    ApiVersion annotation = handlerMethod.getMethodAnnotation(ApiVersion.class);
                    if (annotation == null || !Arrays.asList(annotation.value()).contains("v1")) {
                        return null; // مخفی کردن متدی که نسخه v1 نداره
                    }
                    return operation;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi apiV2() {
        return GroupedOpenApi.builder()
                .group("v2")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    ApiVersion annotation = handlerMethod.getMethodAnnotation(ApiVersion.class);
                    if (annotation == null || !Arrays.asList(annotation.value()).contains("v2")) {
                        return null; // مخفی کردن متدی که نسخه v2 نداره
                    }
                    return operation;
                })
                .build();
    }
}
