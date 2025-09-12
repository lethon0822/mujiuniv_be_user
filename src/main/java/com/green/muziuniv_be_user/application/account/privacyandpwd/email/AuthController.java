package com.green.muziuniv_be_user.application.account.privacyandpwd.email;

import com.green.muziuniv_be_user.application.account.privacyandpwd.email.model.EmailCheckDto;
import com.green.muziuniv_be_user.application.account.privacyandpwd.email.model.EmailRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
public class AuthController {
    private final AuthService authService;

    @PostMapping ("/auth")
    public String sendMail(@RequestBody EmailRequestDto emailRequestDto){
        log.info("이메일 인증 이메일, {}", emailRequestDto.getEmail());
        return authService.authMail(emailRequestDto.getEmail());
    }

    @PostMapping("/check")
    public String checkAuthNum(@RequestBody EmailCheckDto emailCheckDto){
        Boolean checked = authService.checkAll(emailCheckDto.getEmail(),emailCheckDto.getAuthCode());
        if(checked){
            log.info("으에에에에엑");
            return "인증이 완료되었습니다.";
        }
        else {
            throw new InvalidAuthException("인증을 실패하였습니다.");
        }
    }
}