package ir.sinatech.rial_digital_service_bus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sinatech.rial_digital_service_bus.WebClientLoggingFilter;
import ir.sinatech.rial_digital_service_bus.model.UserInfoRequestModel;
import ir.sinatech.rial_digital_service_bus.model.UserInfoResponse;
import ir.sinatech.rial_digital_service_bus.utils.AESCipherUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserInfoService {

    private final WebClient webClient = WebClient.builder()
            .filter(WebClientLoggingFilter.logRequest())
            .filter(WebClientLoggingFilter.logResponse())
            .build();

    public UserInfoService() {
    }

    public String getUserInfo(UserInfoRequestModel req) {
        byte[] decodedBytes = Base64.getDecoder().decode(req.getToken());
        String decodedString = new String(decodedBytes);

        return switch (decodedString) {
            case "1" -> """
                    {
                      "user": {
                        "national_id": "1234567890",
                        "is_legal": false,
                        "mobile": "09123456789",
                        "customer_id": "C123456",
                        "is_bank_customer": true
                      },
                      "Identity": {
                        "id_national": "1234567890",
                        "id_serial_national": "1234567S",
                        "name_first": "string",
                        "first_name_en": "string",
                        "last_name": "string",
                        "en_name_last": "string",
                        "father_name": "string",
                        "birthdate": 13701016,
                        "birthdate_time": "2004-05-01T00:00:00Z",
                        "alive": true,
                        "gender": 1,
                        "number_shenasname": 1234,
                        "shenasname_seri": "34ی",
                        "serial_shenasname": 1234
                      }
                    }
                    """;
            case "2" -> """
                    {
                      "user": {
                        "national_id": "1234567890",
                        "is_legal": false,
                        "mobile": "09123456789",
                        "customer_id": "",
                        "is_bank_customer": false
                      },
                      "Identity": {
                        "id_national": "1234567890",
                        "id_serial_national": "1234567S",
                        "name_first": "string",
                        "first_name_en": "string",
                        "last_name": "string",
                        "en_name_last": "string",
                        "father_name": "string",
                        "birthdate": 13701016,
                        "birthdate_time": "2004-05-01T00:00:00Z",
                        "alive": true,
                        "gender": 1,
                        "number_shenasname": 1234,
                        "shenasname_seri": "34ی",
                        "serial_shenasname": 1234
                      }
                    }
                    """;
            case "3" -> """
                    {
                      "user": {
                        "national_id": "1234567890",
                        "is_legal": false,
                        "mobile": "09123456789",
                        "customer_id": "",
                        "is_bank_customer": false
                      },
                      "Identity": {}
                    }
                    """;
            default -> "{\"error\": \"Invalid token\"}";
        };
    }

    public Optional<UserInfoResponse> login(String token, int appId) throws Exception {
        try {
            String decrypted = AESCipherUtil.decrypt(token);
            var req = new UserInfoRequestModel();
            req.setToken(Base64.getEncoder().encodeToString(decrypted.getBytes(StandardCharsets.UTF_8)));
            String result = getUserInfo(req);
            return Optional.of(new ObjectMapper().readValue(result, UserInfoResponse.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
