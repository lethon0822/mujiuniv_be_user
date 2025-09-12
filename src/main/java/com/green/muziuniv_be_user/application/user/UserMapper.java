package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.ProGetRes;
import com.green.muziuniv_be_user.application.user.model.StudentGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<StudentGetRes> findStudentByUserId(List<Long> userId);

    List<ProGetRes> findProByUserId(List<Long> userId);
}
