package com.green.muziuniv_be_user.application.account;


import com.green.muziuniv_be_user.application.account.model.*;
import com.green.muziuniv_be_user.common.model.JwtUser;
import com.green.muziuniv_be_user.openfeign.semester.SemesterClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
   private final PasswordEncoder passwordEncoder;
   private final AccountMapper accountMapper;
   // private final SemesterClient semesterClient;

   public AccountLoginDto login(AccountLoginReq req) {
      AccountLoginRes res = accountMapper.findByLoginId(req);
      if (res == null) return null; //TODO: 예외 발생



      // 보안상 노출 방지
      res.setPassword(null);
      return AccountLoginDto.builder()
              .accountLoginRes(res)
              .jwtUser(new JwtUser())
              .build();
   }

   // -------------------------------------------------------------

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

}
