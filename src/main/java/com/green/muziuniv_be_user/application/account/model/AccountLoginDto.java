package com.green.muziuniv_be_user.application.account.model;


import com.green.muziuniv_be_user.configuration.model.JwtUser;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AccountLoginDto {
    private AccountLoginRes accountLoginRes; // 응답용
    private JwtUser jwtUser; // JWT 발행 때 사용
}
