package ir.sinatech.rial_digital_service_bus.model;

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
        @JsonProperty("id_national")
        private String idNational;

        @JsonProperty("id_serial_national")
        private String idSerialNational;

        @JsonProperty("name_first")
        private String nameFirst;

        @JsonProperty("first_name_en")
        private String firstNameEn;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("en_name_last")
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

        @JsonProperty("number_shenasname")
        private int numberShenasname;

        @JsonProperty("shenasname_seri")
        private String shenasnameSeri;

        @JsonProperty("serial_shenasname")
        private int serialShenasname;

        public boolean isEmpty() {
            return nameFirst == null &&
                    lastName == null &&
                    idNational == null;
        }
    }
}
