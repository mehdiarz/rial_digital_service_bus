package ir.sinatech.rial_digital_service_bus.controller;

import ir.sinatech.rial_digital_service_bus.model.*;
import ir.sinatech.rial_digital_service_bus.service.GetTokenService;
import ir.sinatech.rial_digital_service_bus.service.UserInfoService;
import ir.sinatech.rial_digital_service_bus.utils.AESCipherUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @ApiVersion({"v1", "v2"})
    @PostMapping("/api/getUserInfo/")
    public String getUserInfo(@RequestBody UserInfoRequestModel req) {
        return userInfoService.getUserInfo(req);
    }



    @ApiVersion("v2")
    @GetMapping("/api/login/")
    public String login(@RequestParam("token") String token, @RequestParam("app_id") int appId) throws Exception {
        Optional<UserInfoResponse> userInfo = userInfoService.login(token, appId);
        if (userInfo.isEmpty()) {
            return "<h1>خطا در دریافت اطلاعات</h1>";
        }
        if(userInfo.get().getUser().isBankCustomer() && (userInfo.get().getIdentity() != null && !userInfo.get().getIdentity().isEmpty())) {
            return "<h1>خوش آمدید (مشتری بانک)</h1>";
        }
        if(!userInfo.get().getUser().isBankCustomer() && (userInfo.get().getIdentity() != null && !userInfo.get().getIdentity().isEmpty())) {
            return "<h1>خوش آمدید (کاربر احراز شده ولی مشتری بانک نیست)</h1>";
        }
        if(!userInfo.get().getUser().isBankCustomer() && (userInfo.get().getIdentity() == null || userInfo.get().getIdentity().isEmpty())) {
            return "<h1>خوش آمدید (کاربر احراز نشده و مشتری بانک نیست)</h1>";
        }
        return "<h1>خطا در ورود</h1>";
    }
}
