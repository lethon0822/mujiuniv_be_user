package com.green.muziuniv_be_user.application.department.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentPutReq {
    private Long deptId;

    @NotNull(message = "학과장을 선택해주십시오.")
    private Long headProfId;

    @NotNull(message = "학과 이름은 필수입니다.")
    private String deptName;

    @NotNull(message = "학과 사무실은 필수입니다")
    private String deptOffice;

    @NotNull(message = "학과 이름은 필수입니다.")
    @Pattern(regexp = "^(\\d{2,3})-(\\d{3})-(\\d{4})$", message = "전화번호 형식이 올바르지 않습니다.")

    private String deptTel;

    @Min(300)
    @Max(450)
    private int deptMaxStd;

    @NotNull(message = "학과 코드는 필수입니다")
    @Pattern(regexp = "^[A-Z]{4}$", message = "학과 코드 형식이 올바르지 않습니다.")
    private String deptCode;

    private String status = "운영중";
}
