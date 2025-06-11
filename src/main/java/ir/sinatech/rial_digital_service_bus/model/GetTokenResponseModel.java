package ir.sinatech.rial_digital_service_bus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTokenResponseModel {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
