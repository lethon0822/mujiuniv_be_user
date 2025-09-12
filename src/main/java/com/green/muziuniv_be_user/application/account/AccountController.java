package com.green.muziuniv_be_user.application.account;

import com.green.muziuniv_be_user.application.account.model.*;
import com.green.muziuniv_be_user.application.account.privacyandpwd.model.PrivacyGetRes;
import com.green.muziuniv_be_user.application.account.privacyandpwd.model.PrivacyPutReq;
import com.green.muziuniv_be_user.application.account.privacyandpwd.model.PwdPutReq;
import com.green.muziuniv_be_user.common.jwt.JwtTokenManager;
import com.green.muziuniv_be_user.common.model.ResultResponse;
import com.green.muziuniv_be_user.common.model.SignedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/logout")
    public ResultResponse<?> logout(HttpServletResponse response) {
        jwtTokenManager.signOut(response);

        return new ResultResponse<>("로그아웃 성공", null);
    }

    // accessToken 재발행
    @PostMapping("/reissue")
    public ResultResponse<?> reissue(HttpServletResponse response, HttpServletRequest request) {
        jwtTokenManager.reissue(request, response);
        return new ResultResponse<>("AccessToken 재발행 성공", null);
    }

    //아이디 찾기
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

    @GetMapping("/profile")
    public ResultResponse<?> getProfileUser(@AuthenticationPrincipal SignedUser signedUser) {
        log.info("profileUserId: {}", signedUser);
        
        return new ResultResponse<>("프로파일 유저 정보", null);
    }

    // 개인정보 조회(주소)
    @GetMapping("/privacy")
    public ResponseEntity<?> selectPrivacy (@AuthenticationPrincipal SignedUser signedUser) {
        PrivacyGetRes result = accountService.selectMyPrivacy(signedUser.signedUserId);
        return ResponseEntity.ok(result);
    }

    // 개인정보 변경
    @PutMapping("/privacy")
    public ResponseEntity<?> updatePrivacy (@AuthenticationPrincipal SignedUser signedUser, @RequestBody PrivacyPutReq req) {
        req.setUserId(signedUser.signedUserId);
        log.info("세션에 저장된 userId={}", req.getUserId());
        int result = accountService.updateMyPrivacy(req);
        return ResponseEntity.ok(result);
    }

    // 비밀번호 변경
    @PutMapping("/renewal")
    public ResponseEntity<?> updatePwd (@AuthenticationPrincipal SignedUser signedUser, @RequestBody PwdPutReq req) {
        int result = accountService.updateMyPwd(req);
        return ResponseEntity.ok(result);
    }


    // 이건 뭔지 모름
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