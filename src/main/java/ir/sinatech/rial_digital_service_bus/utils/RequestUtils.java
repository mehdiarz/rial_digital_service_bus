package ir.sinatech.rial_digital_service_bus.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RequestUtils {

    public static HttpEntity<String> createJsonRequest(String body, boolean byAdmin, AppConfig appConfig) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", byAdmin ? appConfig.getAdminToken() : "3dda4374-2434-4414-9a9e-bdc1d621fa25");
        headers.set("user", byAdmin ? appConfig.getAdminUser() : "9910433274");
        headers.set("branch", appConfig.getBranch());

        return new HttpEntity<>(body, headers);
    }

    public static HttpEntity<Void> createEmptyJsonRequest(boolean byAdmin, AppConfig appConfig) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", byAdmin ? appConfig.getAdminToken() : "3dda4374-2434-4414-9a9e-bdc1d621fa25");
        headers.set("user", byAdmin ? appConfig.getAdminUser() : "9910433274");
        headers.set("branch", appConfig.getBranch());

        return new HttpEntity<>(null, headers);
    }
}
