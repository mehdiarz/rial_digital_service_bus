package ir.sinatech.rial_digital_service_bus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sinatech.rial_digital_service_bus.model.GetTokenRequestModel;
import ir.sinatech.rial_digital_service_bus.model.GetTokenResponseModel;
import ir.sinatech.rial_digital_service_bus.model.UserInfoRequestModel;
import ir.sinatech.rial_digital_service_bus.model.UserInfoResponse;
import ir.sinatech.rial_digital_service_bus.utils.AESCipherUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class GetTokenService {

    public static Optional<GetTokenResponseModel> getToken(GetTokenRequestModel getTokenRequestModel) throws Exception {
        try {
            String userId = getTokenRequestModel.getUserId();
            String encrypted = AESCipherUtil.encrypt(userId);
            var res = new GetTokenResponseModel();
            res.setToken(encrypted);
            return Optional.of(res);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
