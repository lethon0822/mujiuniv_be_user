package com.green.muziuniv_be_user.application.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.muziuniv_be_user.configuration.constants.ConstJwt;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AccountLoginRes {
    private Long userId;
    private String userRole;
    private String userName;
    private int loginId;
    private int semesterId;
    private String deptName;
    private int expiresAt;
    @JsonIgnore
    private String password;

}
