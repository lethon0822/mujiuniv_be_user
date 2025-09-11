package com.green.muziuniv_be_user.application.account;


import com.green.muziuniv_be_user.application.account.model.*;
import com.green.muziuniv_be_user.common.model.JwtUser;
import com.green.muziuniv_be_user.openfeign.semester.SemesterClient;
import com.green.muziuniv_be_user.openfeign.semester.model.SemesterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
   private final PasswordEncoder passwordEncoder;
   private final AccountMapper accountMapper;
   private final SemesterClient semesterClient;

   public AccountLoginDto login(AccountLoginReq req) {
      AccountLoginRes res = accountMapper.findByUserInfo(req);
      if(res == null || !passwordEncoder.matches(req.getPassword(), res.getPassword())) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디/비밀번호를 확인해 주세요.");
      }

      //현재 연도
      int year = LocalDate.now().getYear();
      int month = LocalDate.now().getMonthValue();

      int semester = switch (month){
         case 1,2,3,4,5,6 -> 1;
         case 7,8,9,10,11,12 -> 2;
         default -> throw new IllegalStateException("잘못된 월: " + month);
      };

      int semesterId = nowSemester();
      res.setSemesterId(semesterId);


      // 보안상 노출 방지
      res.setPassword(null);
      return AccountLoginDto.builder()
              .accountLoginRes(res)
              .jwtUser(new JwtUser(res.getUserId(), res.getUserRole()))
              .build();
   }


   public String encodePassword(String rawPassword) {
      return passwordEncoder.encode(rawPassword);
   }

   // 비밀번호 일치 여부 검사
   public boolean matchesPassword(String rawPassword, String storedPassword) {
      // bcrypt로 암호화된 비밀번호는 항상 $2a$ / $2b$ / $2y$ 로 시작
      boolean isBcrypt = storedPassword.startsWith("$2a$")
              || storedPassword.startsWith("$2b$")
              || storedPassword.startsWith("$2y$");

      if (isBcrypt) {
         // bcrypt 비교
         return passwordEncoder.matches(rawPassword, storedPassword);
      } else {
         // 초기 비밀번호(평문) 비교
         return storedPassword.equals(rawPassword);
      }
   }

   // -------------------------------------------------------------

   public AccountFindIdRes findIdByEmailAndPhone(AccountFindIdReq req) {
      AccountFindIdRes res = accountMapper.findIdByEmailAndPhone(req);
      return res;
   }

   public int nowSemester(){
      int year = LocalDate.now().getYear();

      int month = LocalDate.now().getMonthValue();
      int semester = switch (month){
         case 1,2,3,4,5,6 -> 1;
         case 7,8,9,10,11,12 -> 2;
         default -> throw new IllegalStateException("잘못된 월: " + month);
      };
      SemesterDto result = semesterClient.getSemesterId(year,semester);

      return result.getSemesterId();
   }
}
