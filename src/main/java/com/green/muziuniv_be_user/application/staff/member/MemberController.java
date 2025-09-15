package com.green.muziuniv_be_user.application.staff.member;

import com.green.muziuniv_be_user.application.staff.member.model.MemberGetReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/staff/member")
public class MemberController {
    private final MemberService memberService;

    //  추후 User 폴더 생성시 이 파일의 내용은 user로 이동
    @GetMapping
    public ResponseEntity<?> getMember(@ModelAttribute MemberGetReq req) {
        return ResponseEntity.ok(memberService.findUser(req));
    }

}
