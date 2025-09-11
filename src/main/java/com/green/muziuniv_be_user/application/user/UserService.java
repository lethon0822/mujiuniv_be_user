package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.department.DepartmentRepository;
import com.green.muziuniv_be_user.application.user.Repository.ProfessorRepository;
import com.green.muziuniv_be_user.application.user.Repository.StudentRepository;
import com.green.muziuniv_be_user.application.user.Repository.UserRepository;
import com.green.muziuniv_be_user.application.user.model.ProGetRes;
import com.green.muziuniv_be_user.application.user.model.StudentGetRes;
import com.green.muziuniv_be_user.application.user.model.UserGetRes;
import com.green.muziuniv_be_user.common.model.SignedUser;
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
    private final DepartmentRepository departmentRepository;

    //통신용
    public List<StudentGetRes> studentInfoList(List<Long> userId){
        return userMapper.findStudentByUserId(userId);
    }

    //통신용
    public List<ProGetRes> ProInfoList(List<Long> userId){
        return userMapper.findProByUserId(userId);
    }

    //유저 프로필
    public UserGetRes userInfoDto(SignedUser signedUser){
        User user = userRepository.findById(signedUser.signedUserId)
                    .orElseThrow(() -> new RuntimeException("유저가 없습니다"));

        UserGetRes result = changeUserDto(user);

        //ToDO: 유정정보 넣기


        return null;
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
        if(user.getUserRole() == "student"){
            Student student = studentRepository.findById(user.getUserId())
                    .orElseThrow(() -> new RuntimeException("학생 기록이 없습니다"));

          return studentInfo(student);
        }
        Professor professor = professorRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("교수 기록이 없습니다"));

        return proInfo(professor);
    }
}
