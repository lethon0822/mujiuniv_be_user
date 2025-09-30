package com.green.muziuniv_be_user.application.user;



import com.green.muziuniv_be_user.application.user.model.*;
import com.green.muziuniv_be_user.application.user.Repository.ProfessorRepository;
import com.green.muziuniv_be_user.application.user.Repository.StudentRepository;
import com.green.muziuniv_be_user.application.user.Repository.UserRepository;
import com.green.muziuniv_be_user.configuration.model.SignedUser;
import com.green.muziuniv_be_user.configuration.util.ImgUploadManager;
import com.green.muziuniv_be_user.entity.professor.Professor;
import com.green.muziuniv_be_user.entity.student.Student;
import com.green.muziuniv_be_user.entity.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final ImgUploadManager imgUploadManager;


    //통신용
    public List<StudentGetDto> studentInfoList(List<Long> userId){
        return userMapper.findStudentByUserId(userId);
    }

    //통신용
    public Map<Long, UserInfoGetDto> UserInfoList(List<Long> userId){
        List<UserInfoGetDto> userInfo = userMapper.findUserInfoByUserId(userId);
        Map<Long, UserInfoGetDto> result = userInfo.stream()
                .collect(Collectors.toMap(
                        item -> item.getUserId(),
                        item -> item
                ));
        return result;
    }

    //통신용 학과코드 가져오기
    public String ProDeptCode(Long userId){
        Professor professor = professorRepository.findById(userId).orElseThrow(() -> new RuntimeException("문제발생"));
        return professor.getDepartment().getDeptCode();
    }

    // 유저 목록 조회용(staff 기능)
    public List<MemberGetRes> findUser(MemberGetReq req){
        return userMapper.findUser(req);
    }

    /**
     * 유저 프로필 메소드
     * 1차적으로 User entity 정보를 가져옵니다\n
     * changeUserDto는 분기처리 담당입니다 User 객체를 넘겨주면 userRole을 구분하여
     * studentInfo 또는 proInfo 메소드로 넘겨줍니다
     * 각 메소드에서 UserGetRes를 생성 후 userInfoDto 메소드로 넘어와 공통 정보를 이어서 넣은 후 반환합니다
     *
     * @param signedUser 현재 로그인한 사용자의 PK값
     * @return UserGetRes 유저 정보를 담은 DTO
     *
     *
     */
    public UserGetRes userInfoDto(SignedUser signedUser){
        User user = userRepository.findById(signedUser.signedUserId)
                    .orElseThrow(() -> new RuntimeException("유저가 없습니다"));
        if ("staff".equals(user.getUserRole())) {
            return UserGetRes.builder().build();
        }

        UserGetRes data = changeUserDto(user);

        UserGetRes finalUserInfo = data.toBuilder()
                .loginId(user.getLoginId())
                .userName(user.getUserName())
                .birthDate(user.getBirthDate().toString())
                .email(user.getEmail())
                .postcode(user.getAddress().getPostcode())
                .address(user.getAddress().getAddress())
                .addDetail(user.getAddress().getAddDetail())
                .phone(user.getPhone())
                .userPic(user.getUserPic())
                .build();

        return finalUserInfo;
    }

    private UserGetRes studentInfo (Student student){
        UserGetRes result = UserGetRes.builder()
                .deptName(student.getDepartment().getDeptName())
                .grade(student.getGrade())
                .entDate(student.getEntDate().toString())
                .semester(student.getSemester())
                .status(student.getStatus())
                .build();
        return result;
    }

    private UserGetRes proInfo(Professor professor){
        UserGetRes result = UserGetRes.builder()
                .status(professor.getStatus())
                .deptName(professor.getDepartment().getDeptName())
                .hireDate(professor.getHireDate().toString())
                .build();
        return result;
    }

    private UserGetRes changeUserDto(User user){
        if("student".equals(user.getUserRole())){
            Student student = studentRepository.findById(user.getUserId())
                    .orElseThrow(() -> new RuntimeException("학생 기록이 없습니다"));

          return studentInfo(student);
        }
        Professor professor = professorRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("교수 기록이 없습니다"));

        return proInfo(professor);
    }

    // ------------------------------------------------------------

    @Transactional
    public String postProfilePic(long signedUserId, MultipartFile pic) {
        User user = userRepository.findById(signedUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));

        String savedFileName = imgUploadManager.saveProfilePic(user.getUserId(), pic);
        user.setUserPic(savedFileName);

        return savedFileName;
    }

    public UserProfileGetRes getProfileUser(UserProfileGetDto dto) {
        return userMapper.findProfileByUserId(dto);
    }

    @Transactional
    public String patchProfilePic(long signedUserId, MultipartFile pic) {
        User user = userRepository.findById(signedUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));
        imgUploadManager.removeProfileDirectory(signedUserId);
        String savedFileName = imgUploadManager.saveProfilePic(signedUserId, pic);
        user.setUserPic(savedFileName);
        return savedFileName;
    }

    @Transactional
    public void deleteProfilePic(long signedUserId) {
        User user = userRepository.findById(signedUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));
        imgUploadManager.removeProfileDirectory(signedUserId);
        user.setUserPic(null);
    }
}
