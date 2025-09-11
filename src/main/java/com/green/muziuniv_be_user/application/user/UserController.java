package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.common.model.ResultResponse;
import com.green.muziuniv_be_user.common.model.SignedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResultResponse<?> getUserInfo(@AuthenticationPrincipal SignedUser signedUser){
        log.info("dkdk:{}",signedUser);
        return new ResultResponse<>("susu", null);
    }

}
