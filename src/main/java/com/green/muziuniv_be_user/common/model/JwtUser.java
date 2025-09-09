package com.green.muziuniv_be_user.common.model;


import com.green.muziuniv_be_user.common.enumcode.model.EnumUserRole;
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
