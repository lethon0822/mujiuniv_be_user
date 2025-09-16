package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.ProGetDto;
import com.green.muziuniv_be_user.application.user.model.StudentGetDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<StudentGetDto> findStudentByUserId(List<Long> userId);

    List<ProGetDto> findProByUserId(List<Long> userId);
}
