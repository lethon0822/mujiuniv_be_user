package com.green.muziuniv_be_user.application.account;


import com.green.muziuniv_be_user.application.account.model.*;
import com.green.muziuniv_be_user.common.jwt.JwtTokenManager;
import com.green.muziuniv_be_user.common.model.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/login")
    public ResultResponse<?> login(HttpServletResponse response,
                                @RequestBody AccountLoginReq req) {
        AccountLoginDto result = accountService.login(req);

        //토큰 생성
        jwtTokenManager.issue(response,result.getJwtUser());
        return new ResultResponse<>("로그인 성공", result.getAccountLoginRes());
    }


    @GetMapping("/id")
    public ResponseEntity<?> findId (@ModelAttribute AccountFindIdReq req){
        AccountFindIdRes result = accountService.findIdByEmailAndPhone(req);
        log.info("req:{}",req);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(Map.of("loginId", auth.getName()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        // SecurityContext 비우기
        SecurityContextHolder.clearContext();
        // 세션 무효화
        HttpSession s = req.getSession(false);
        if (s != null) s.invalidate();
        return ResponseEntity.ok().build();
    }
    // AccountController.java
    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(HttpServletRequest req, Authentication auth) {
        HttpSession s = req.getSession(false);
        String sid = (s != null) ? s.getId() : "no-session";
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("sid", sid, "auth", "anonymous"));
        }
        return ResponseEntity.ok(Map.of(
                "sid", sid,
                "principal", auth.getName(),
                "authorities", auth.getAuthorities().toString()
        ));
    }
}