package com.green.muziuniv_be_user.application.account.model.excelFile;



import java.time.LocalDate;

public record ExcelUserRow(
        int rowNumber,       // 엑셀 상 몇 번째 행인지 (에러 메시지에 쓰려고 따로 저장)
        String userName,     // "이름" 열
        LocalDate birthDate, // "생년월일" 열
        String gender,       // "성별" 열
        String email,        // "이메일" 열
        String phone,        // "전화번호" 열
        ExcelAddress address,
        String deptName,      // "학과" 열
        LocalDate startDate
) {
}
