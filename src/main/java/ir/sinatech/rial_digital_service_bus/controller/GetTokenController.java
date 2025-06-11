package ir.sinatech.rial_digital_service_bus.controller;


import ir.sinatech.rial_digital_service_bus.model.ApiVersion;
import ir.sinatech.rial_digital_service_bus.model.GetTokenRequestModel;
import ir.sinatech.rial_digital_service_bus.model.GetTokenResponseModel;
import ir.sinatech.rial_digital_service_bus.service.GetTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GetTokenController {
    @ApiVersion("v2")
    @PostMapping("/api/getToken/")
    public Optional<String> getToken(@RequestBody GetTokenRequestModel req) {
        try {
            return GetTokenService.getToken(req)
                    .map(GetTokenResponseModel::getToken);
        }catch (Exception e) {
            return Optional.empty();
        }
    }
}
