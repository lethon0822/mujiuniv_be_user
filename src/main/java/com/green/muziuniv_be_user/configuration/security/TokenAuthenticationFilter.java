package com.green.muziuniv_be_user.configuration.security;


import com.green.muziuniv_be_user.configuration.constants.ConstJwt;
import com.green.muziuniv_be_user.configuration.jwt.JwtTokenManager;
import com.green.muziuniv_be_user.configuration.model.SignedUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenManager jwtTokenManager;
    private final ConstJwt constJwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request.getRequestURI(): {}", request.getRequestURI());

        String signedUserId = request.getHeader(constJwt.claimKey);
        log.info("signedUserId: {}", signedUserId);

        if (signedUserId != null) {
            //UserPrincipal userPrincipal = objectMapper.readValue(signedUserJson, UserPrincipal.class);
            SignedUser signedUser = new SignedUser(Long.parseLong(signedUserId));
            Authentication auth = new UsernamePasswordAuthenticationToken(signedUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response); //다음 필터에게 req, res 넘기기
    }
}
