package com.green.muziuniv_be_user.staff.member;

import com.green.muziuniv_be_user.staff.member.model.MemberGetReq;
import com.green.muziuniv_be_user.staff.member.model.MemberGetRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    public List<MemberGetRes> findUser(MemberGetReq req){
        return memberMapper.findUser(req);
    }
}
