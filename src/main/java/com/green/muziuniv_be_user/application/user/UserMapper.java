package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<StudentGetDto> findStudentByUserId(List<Long> userId);

    List<UserInfoGetDto> findUserInfoByUserId(List<Long> userIdList);

    List<MemberGetRes> findUser(MemberGetReq req);

    UserProfileGetRes findProfileByUserId(UserProfileGetDto dto);

    // 단일 유저 조회
    UserInfoGetDto findUserById(@Param("userId") Long userId);
}
