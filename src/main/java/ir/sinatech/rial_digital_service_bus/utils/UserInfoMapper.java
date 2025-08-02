package ir.sinatech.rial_digital_service_bus.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sinatech.rial_digital_service_bus.model.UserInfoResponse;

public class UserInfoMapper {

    public static UserInfoResponse mapToDesiredFormat(String originalJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // خواندن JSON اصلی به یک ساختار موقت
        UserInfoResponse original = mapper.readValue(originalJson, UserInfoResponse.class);

        // ایجاد شیء پاسخ با ساختار مورد نظر
        UserInfoResponse response = new UserInfoResponse();

        // مپ کردن بخش user
        UserInfoResponse.User user = new UserInfoResponse.User();
        user.setNationalId(original.getUser().getNationalId());
        user.setLegal(original.getUser().isLegal());
        user.setMobile(original.getUser().getMobile());
        user.setCustomerId(original.getUser().getCustomerId());
        user.setBankCustomer(original.getUser().isBankCustomer());
        response.setUser(user);

        // مپ کردن بخش identity (اگر وجود دارد)
        if (original.getIdentity() != null) {
            UserInfoResponse.Identity identity = new UserInfoResponse.Identity();
            identity.setIdNational(original.getIdentity().getIdNational());
            identity.setIdSerialNational(original.getIdentity().getIdSerialNational());
            identity.setNameFirst(original.getIdentity().getNameFirst());
            identity.setFirstNameEn(original.getIdentity().getFirstNameEn());
            identity.setLastName(original.getIdentity().getLastName());
            identity.setEnNameLast(original.getIdentity().getEnNameLast());
            identity.setFatherName(original.getIdentity().getFatherName());
            identity.setBirthdate(original.getIdentity().getBirthdate());

            identity.setBirthdateTime(original.getIdentity().getBirthdateTime());
            identity.setAlive(original.getIdentity().isAlive());
            identity.setGender(original.getIdentity().getGender());
            identity.setNumberShenasname(original.getIdentity().getNumberShenasname());

            identity.setShenasnameSeri(original.getIdentity().getShenasnameSeri());

            try {
                identity.setSerialShenasname(original.getIdentity().getSerialShenasname());
            } catch (NumberFormatException e) {
                identity.setSerialShenasname(0);
            }

            response.setIdentity(identity);
        } else {
            response.setIdentity(new UserInfoResponse.Identity());
        }

        return response;
    }
}