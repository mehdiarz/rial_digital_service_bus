package ir.sinatech.rial_digital_service_bus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfoResponse {

    @JsonProperty("user")
    private User user;

    @JsonProperty("Identity")
    private Identity identity;

    @Data
    public static class User {
        @JsonProperty("national_id")
        private String nationalId;

        @JsonProperty("is_legal")
        private boolean isLegal;

        @JsonProperty("mobile")
        private String mobile;

        @JsonProperty("customer_id")
        private String customerId;

        @JsonProperty("is_bank_customer")
        private boolean isBankCustomer;
    }

    @Data
    public static class Identity {
        @JsonProperty("national_id")
        private String idNational;

        @JsonProperty("national_serial_id")
        private String idSerialNational;

        @JsonProperty("first_name")
        private String nameFirst;

        @JsonProperty("first_name_en")
        private String firstNameEn;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("last_name_en")
        private String enNameLast;

        @JsonProperty("father_name")
        private String fatherName;

        @JsonProperty("birthdate")
        private Integer birthdate;

        @JsonProperty("birthdate_time")
        private String birthdateTime;

        @JsonProperty("alive")
        private boolean alive;

        @JsonProperty("gender")
        private int gender;

        @JsonProperty("shenasname_number")
        private int numberShenasname;

        @JsonProperty("shenasname_seri")
        private String shenasnameSeri;

        @JsonProperty("shenasname_serial")
        private int serialShenasname;

        @JsonIgnore
        public boolean isIdentityEmpty() {
            return nameFirst == null &&
                    lastName == null &&
                    idNational == null;
        }
    }
}
