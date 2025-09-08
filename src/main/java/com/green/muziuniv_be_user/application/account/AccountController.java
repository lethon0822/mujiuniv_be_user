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
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/login")
    public ResultResponse<?> login(HttpServletResponse response,
                                @RequestBody AccountLoginReq req) {
        AccountLoginDto result = accountService.login(req);

        //토큰 생성
        jwtTokenManager.issue(response,result.getJwtUser());






// ----------------------------------------------------------------------------
////        // 세션 강제 생성
//        HttpSession session = req.getSession(true);
//        session.setAttribute("userId",result.getUserId());
//
//        // 스프링 시큐리티 인증객체 생성
//        var auth = new UsernamePasswordAuthenticationToken(
//                result.getLoginId(),        // principal (식별자)
//                null,                       // credentials (비밀번호는 저장 안 함)
//                List.of(new SimpleGrantedAuthority("ROLE_USER")) // 권한 필요 시 매핑
//        );
//
//        // SecurityContextHolder 에 등록
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(auth);
//        SecurityContextHolder.setContext(context);
//
//        // 세션에 컨텍스트 저장 (JSESSIONID와 매핑)
//        new HttpSessionSecurityContextRepository().saveContext(context, req, res);
//
//        log.info("[login] ok, sessionId={}", session.getId());
//        return ResponseEntity.ok(result);
        return new ResultResponse<>("로그인 성공", 1);
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