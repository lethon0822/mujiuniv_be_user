package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<StudentGetDto> findStudentByUserId(List<Long> userId);

    List<UserInfoGetDto> findUserInfoByUserId(List<Long> userIdList);

    List<MemberGetRes> findUser(MemberGetReq req);

}
