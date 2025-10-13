package com.green.muziuniv_be_user.application.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoGetDto {
    private Long userId;
    private String userName;
    private String deptName;
    private String userRole;   // ✅ 이거 추가 (PROFESSOR / STUDENT 구분용)
    private String status;     // 혹시 상태값도 넘겨줄 수 있으면 같이 유지
}
