package com.green.muziuniv_be_user.application.user;



import com.green.muziuniv_be_user.application.user.model.*;
import com.green.muziuniv_be_user.application.user.Repository.ProfessorRepository;
import com.green.muziuniv_be_user.application.user.Repository.StudentRepository;
import com.green.muziuniv_be_user.application.user.Repository.UserRepository;
import com.green.muziuniv_be_user.configuration.model.SignedUser;
import com.green.muziuniv_be_user.entity.professor.Professor;
import com.green.muziuniv_be_user.entity.student.Student;
import com.green.muziuniv_be_user.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;


    //통신용
    public List<StudentGetDto> studentInfoList(List<Long> userId){
        return userMapper.findStudentByUserId(userId);
    }

    //통신용
    public List<UserInfoGetDto> UserInfoList(List<Long> userId){
        return userMapper.findUserInfoByUserId(userId);
    }

    // 유저 목록 조회용(staff 기능)


    public List<MemberGetRes> findUser(MemberGetReq req){
        return userMapper.findUser(req);
    }


    /**
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

    }
