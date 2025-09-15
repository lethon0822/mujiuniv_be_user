package com.green.muziuniv_be_user.application.staff.member;

import com.green.muziuniv_be_user.application.staff.member.model.MemberGetReq;
import com.green.muziuniv_be_user.application.staff.member.model.MemberGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberGetRes> findUser(MemberGetReq req);
}
