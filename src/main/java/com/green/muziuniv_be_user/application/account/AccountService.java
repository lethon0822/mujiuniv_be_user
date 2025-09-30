package com.green.muziuniv_be_user.application.account;


import com.green.muziuniv_be_user.application.account.model.*;
import com.green.muziuniv_be_user.application.account.privacyandpwd.model.*;
import com.green.muziuniv_be_user.application.department.DepartmentService;
import com.green.muziuniv_be_user.application.department.model.DeptNameList;
import com.green.muziuniv_be_user.application.user.Repository.ProfessorRepository;
import com.green.muziuniv_be_user.application.user.Repository.StudentRepository;
import com.green.muziuniv_be_user.application.user.Repository.UserRepository;
import com.green.muziuniv_be_user.configuration.constants.ConstJwt;
import com.green.muziuniv_be_user.configuration.model.JwtUser;
//import com.green.muziuniv_be_user.configuration.util.ImgUploadManager;
import com.green.muziuniv_be_user.entity.department.Department;
import com.green.muziuniv_be_user.entity.professor.Professor;
import com.green.muziuniv_be_user.entity.student.Student;
import com.green.muziuniv_be_user.entity.user.Address;
import com.green.muziuniv_be_user.entity.user.User;
import com.green.muziuniv_be_user.openfeign.semester.SemesterClient;
import com.green.muziuniv_be_user.openfeign.semester.model.SemesterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
   private final PasswordEncoder passwordEncoder;
   private final AccountMapper accountMapper;
   private final SemesterClient semesterClient;
   private final UserRepository userRepository;
   private final ProfessorRepository professorRepository;
   private final StudentRepository studentRepository;
//   private final ImgUploadManager imgUploadManager;
   private final DepartmentService departmentService;
   private final ConstJwt constJwt;

   public AccountLoginDto login(AccountLoginReq req) {
      AccountLoginRes res = accountMapper.findByUserInfo(req);
      if(res == null || !passwordEncoder.matches(req.getPassword(), res.getPassword())) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디/비밀번호를 확인해 주세요.");
      }
      res.setExpiresAt(constJwt.accessTokenCookieValiditySeconds);

      int semesterId = nowSemester();
      res.setSemesterId(semesterId);
   //   res.setSemesterId(10);

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

   // 아이디 찾기
   public AccountFindIdRes findIdByEmailAndPhone(AccountFindIdReq req) {
      AccountFindIdRes res = accountMapper.findIdByEmailAndPhone(req);
      return res;
   }


   public PrivacyGetRes selectMyPrivacy(long loginId) {
      return accountMapper.selectMyPrivacy(loginId);
   }

   public int updateMyPrivacy (PrivacyPutReq req) {
      return accountMapper.updateMyPrivacy(req);
   }


   public int updateMyPwd(PwdPutReq req) {
      // 들어온 요청 값 확인
      log.info("요청된 이메일: {}", req.getEmail());
      log.info("요청된 비밀번호(평문): {}", req.getPassword());

      // 비밀번호 해시 처리
      String hashedPw = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
      log.info("해시된 비밀번호: {}", hashedPw);

      // 새 DTO 생성
      PwdPutReq result = new PwdPutReq(req.getEmail(), hashedPw);

      // DB 업데이트 실행
      int updateCount = accountMapper.updateMyPwd(result);
      log.info("업데이트 결과 건수: {}", updateCount);

      return updateCount;
   }


   /**
    * 현재 학기 가져오는 메소드(서버 투 서버 통신)
    * */
   private int nowSemester(){
      int year = LocalDate.now().getYear();

      int month = LocalDate.now().getMonthValue();
      int semester = switch (month){
         case 1,2,3,4,5,6 -> 1;
         case 7,8,9,10,11,12 -> 2;
         default -> throw new IllegalStateException("잘못된 월: " + month);
      };
      String scheduleType=""; //TODO: 어케든해봐라
      SemesterDto result = semesterClient.getSemesterId(year,semester);

      return result.getSemesterId();
   }


   /**
    * 엑셀 데이터를 활용하는 계정 생성 메소드 권한은 staff만
    * user 업로드 후 student와 professor도 입력
    * */
   @Transactional
   public void processExcelFile(MultipartFile excel, String userRole)throws IOException {

      Workbook workbook = null;
      // InputStream은 자바에서 데이터를 읽어오는 통로, 추상클래스
      // 업로드 된 엑셀 파일의 내용을 읽어오는 통로를 만드는 것
      try (InputStream excelInputStream = excel.getInputStream()) {
         String fileName = excel.getOriginalFilename();

         if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(excelInputStream);   // 2003
         } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(excelInputStream);   // 2007 이상
         } else {
            throw new IllegalArgumentException("지원하지 않는 엑셀 형식입니다.");
         }

         List<DeptNameList> deptRes = departmentService.deptName();
         Map<String, Long> deptResMap = deptRes.stream().collect(Collectors.toMap(dept -> dept.getDeptName(), dept -> dept.getDeptId()));

         // 첫 번째 시트 가져오기
         Sheet sheet = workbook.getSheetAt(0);

         // 저장 실패 행 목록
         List<FailedRow> failedRows = new ArrayList<>();

         // DB에 저장된 학번, 사번 MAX값 가져오기
         //int logId = Integer.parseInt(userMapper.findStudentMaxLoginIdByUserRole(userRole))+1;
         Integer logId = Integer.parseInt(accountMapper.findMaxLoginId(userRole))+1;

         //첫 행 건너뜀
         for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
               continue; // 빈 행은 건너뜁니다.
            }
            //delete로 셀을 삭제시 null이 됨 때문에 null 체크도 하기
            Cell cell = row.getCell(i);
            if (cell == null || cell.toString().trim().isEmpty()) continue;

            try{
               // bDay 날짜를 문자열로 바꾸고 비밀번호 설정
               String bDay = changeString(row.getCell(1));
               String hashedPw = firstPwd(bDay);

               User user = User.builder()
                       .userRole(userRole)
                       .loginId(String.valueOf(logId))
                       .password(hashedPw)
                       .userName(row.getCell(0).getStringCellValue())
                       .birthDate(row.getCell(1).getLocalDateTimeCellValue().toLocalDate())
                       .gender(row.getCell(2).getStringCellValue())
                       .email(row.getCell(3).getStringCellValue())
                       .phone(row.getCell(4).getStringCellValue())
                       .address(finalAddress(row))
                       .build();

               //학과명 map으로 변환
               Long id = deptResMap.get(row.getCell(8).getStringCellValue());
               Department department = Department.builder().deptId(id).build();

               // 매핑된 DTO를 DB에 저장
               userRepository.save(user);
               logId += 1;

               if(userRole.equals("professor")){
                  insertProfessor(row, user, department);
               }else{
                  insertStudent(row,user, department);
               }

            } catch (Exception e){
               throw e;
            }
         }
      } catch (Exception e) {
         // RuntimeException 발생 시 자동으로 롤백됩니다.
         // 데이터베이스에 저장된 중간 데이터가 모두 취소됩니다.
         throw new RuntimeException("파일 처리 중 오류가 발생했습니다. 파일을 확인해주십시오.", e);
      }
   }

   /**
    * 날짜 변환
    */
   private String changeString(Cell cell){
      if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
         // 엑셀 날짜를 Date 객체로 가져오기
         Date date = cell.getDateCellValue();

         // 원하는 문자열 형식으로 변환
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         return sdf.format(date);
      } else {
         // 혹시 문자열로 입력된 경우
         DataFormatter formatter = new DataFormatter();
         return formatter.formatCellValue(cell);
      }

   }

   /**
    * 주소 정보
    */
   private Address finalAddress(Row row){
      String postcode = String.valueOf((long)row.getCell(5).getNumericCellValue());
      String address = row.getCell(6).getStringCellValue();
      String addDetail = row.getCell(7).getStringCellValue();
      return new Address(postcode,address,addDetail);
   }

   /**
    * 초기 비밀번호 설정(생년월일)
    */
   private String firstPwd(String bDay){
      // 생일 String 에서 숫자부분만 남김
      String pwd = bDay.replace("-","");
      return passwordEncoder.encode(pwd);
   }

   /**
    * 학생 테이블에 insert
    */
   private void insertStudent (Row row, User user, Department department) {
      try {
         Student student = Student.builder()
                 .user(user)
                 .department(department)
                 .entDate(row.getCell(9).getLocalDateTimeCellValue().toLocalDate())
                 .build();
         studentRepository.save(student);
      } catch (Exception e) {
         // RuntimeException 발생 시 자동으로 롤백됩니다.
         // 데이터베이스에 저장된 중간 데이터가 모두 취소됩니다.
         throw new RuntimeException("학생 정보 저장 중 오류가 발생하였습니다", e);
      }
   }

   /**
    * 교수 테이블에 추가
    */
   private void insertProfessor (Row row, User user, Department department){
      try {
         Professor professor = Professor.builder()
                 .user(user)
                 .department(department)
                 .hireDate(row.getCell(9).getLocalDateTimeCellValue().toLocalDate())
                 .build();
         professorRepository.save(professor);
      } catch (Exception e) {
         // RuntimeException 발생 시 자동으로 롤백됩니다.
         // 데이터베이스에 저장된 중간 데이터가 모두 취소됩니다.
         throw new RuntimeException("교수 정보 저장 중 오류가 발생하였습니다", e);
      }
   }

   // 프로파일 이미지 저장
   public String postProfilePic(long signedUserId, MultipartFile pic) {

      return null;
   }


}

