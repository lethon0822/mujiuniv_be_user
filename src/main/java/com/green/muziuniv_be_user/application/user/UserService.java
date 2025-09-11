package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.department.DepartmentRepository;
import com.green.muziuniv_be_user.application.user.Repository.UserRepository;
import com.green.muziuniv_be_user.application.user.model.ProGetRes;
import com.green.muziuniv_be_user.application.user.model.StudentGetRes;
import com.green.muziuniv_be_user.application.user.model.UserGetRes;
import com.green.muziuniv_be_user.common.model.SignedUser;
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
    public UserGetRes UserInfo(SignedUser signedUser){
        return null;
    }
}
