package ir.sinatech.rial_digital_service_bus.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sinatech.rial_digital_service_bus.WebClientLoggingFilter;
import ir.sinatech.rial_digital_service_bus.model.UserInfoRequestModel;
import ir.sinatech.rial_digital_service_bus.model.UserInfoResponse;
import ir.sinatech.rial_digital_service_bus.utils.AESCipherUtil;
import ir.sinatech.rial_digital_service_bus.utils.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.json.JSONObject;

@Service
public class UserInfoService {

    @Autowired
    private ProcessService processService;

    private final WebClient webClient = WebClient.builder()
            .filter(WebClientLoggingFilter.logRequest())
            .filter(WebClientLoggingFilter.logResponse())
            .build();

    public UserInfoService() {
    }

    public String getUserInfo(UserInfoRequestModel req) throws Exception {
//        byte[] decodedBytes = Base64.getDecoder().decode(req.getToken());
//        String decodedString = new String(decodedBytes);
        String decryptToken = "";

        try {
             decryptToken = AESCipherUtil.decrypt(req.getToken());
        }catch (Exception e){
             decryptToken = "";
        }

        Map<String, Object> rialDigitlInput = new HashMap<>();


        rialDigitlInput.putIfAbsent("userId", decryptToken);
        rialDigitlInput.putIfAbsent("token", req.getToken());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInput = "";
        jsonInput = objectMapper.writeValueAsString(rialDigitlInput);


        List<String> empty = new ArrayList<String>();

        Map<String, Object> rialDigitalMPVars = processService.startProcessWithReturn("Process_MPSina_RialDigital" , empty, jsonInput, true);

        String statusJson = (String) rialDigitalMPVars.get("status");
        JSONObject statusObj = new JSONObject(statusJson);
        if("401".equals(statusObj.getString("statusCode")) || "403".equals(statusObj.getString("statusCode")) || "402".equals(statusObj.getString("statusCode"))) {
            Map<String, Object> status = new HashMap<>();
            status.put("statusCode", statusObj.getString("statusCode"));
            status.put("statusMessage", statusObj.getString("errorMessage"));
            return objectMapper.writeValueAsString(status);
        }else {

            String originalJson = rialDigitalMPVars.get("UserInfo").toString();
            UserInfoResponse mappedResponse = UserInfoMapper.mapToDesiredFormat(originalJson);
            return objectMapper.writeValueAsString(mappedResponse);

        }

//        return switch (decodedString) {
//            case "1" -> """
//                    {
//                      "user": {
//                        "national_id": "1234567890",
//                        "is_legal": false,
//                        "mobile": "09123456789",
//                        "customer_id": "C123456",
//                        "is_bank_customer": true
//                      },
//                      "Identity": {
//                        "id_national": "1234567890",
//                        "id_serial_national": "1234567S",
//                        "name_first": "string",
//                        "first_name_en": "string",
//                        "last_name": "string",
//                        "en_name_last": "string",
//                        "father_name": "string",
//                        "birthdate": 13701016,
//                        "birthdate_time": "2004-05-01T00:00:00Z",
//                        "alive": true,
//                        "gender": 1,
//                        "number_shenasname": 1234,
//                        "shenasname_seri": "34ی",
//                        "serial_shenasname": 1234
//                      }
//                    }
//                    """;
//            case "2" -> """
//                    {
//                      "user": {
//                        "national_id": "1234567890",
//                        "is_legal": false,
//                        "mobile": "09123456789",
//                        "customer_id": "",
//                        "is_bank_customer": false
//                      },
//                      "Identity": {
//                        "id_national": "1234567890",
//                        "id_serial_national": "1234567S",
//                        "name_first": "string",
//                        "first_name_en": "string",
//                        "last_name": "string",
//                        "en_name_last": "string",
//                        "father_name": "string",
//                        "birthdate": 13701016,
//                        "birthdate_time": "2004-05-01T00:00:00Z",
//                        "alive": true,
//                        "gender": 1,
//                        "number_shenasname": 1234,
//                        "shenasname_seri": "34ی",
//                        "serial_shenasname": 1234
//                      }
//                    }
//                    """;
//            case "3" -> """
//                    {
//                      "user": {
//                        "national_id": "0011296585",
//                        "is_legal": false,
//                        "mobile": "09128095806",
//                        "customer_id": "2617711",
//                        "is_bank_customer": false
//                      },
//                      "Identity": {}
//                    }
//                    """;
//            default -> "{\"error\": \"Invalid token\"}";
//        };
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
