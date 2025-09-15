package com.green.muziuniv_be_user.staff.member;

import com.green.muziuniv_be_user.staff.member.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberGetRes> findUser(MemberGetReq req);
}
