package com.green.muziuniv_be_user.configuration.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class JwtUser {
    private Long signedUserId;
    private String role;
}
