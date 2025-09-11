package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.ProGetRes;
import com.green.muziuniv_be_user.application.user.model.StudentGetRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    public List<StudentGetRes> studentInfoList(List<Long> userId){
        return userMapper.findStudentByUserId(userId);
    }

    public List<ProGetRes> ProInfoList(List<Long> userId){
        return userMapper.findProByUserId(userId);
    }
}
