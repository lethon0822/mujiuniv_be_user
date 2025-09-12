package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.ProGetRes;
import com.green.muziuniv_be_user.application.user.model.StudentGetRes;
import com.green.muziuniv_be_user.application.user.model.UserGetRes;
import com.green.muziuniv_be_user.common.model.ResultResponse;
import com.green.muziuniv_be_user.common.model.SignedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 통신용
    @PostMapping("/student")
    public ResultResponse<?> getStudentInfo(@RequestBody Map<String, List<Long>> request) {
        List<Long> userId = request.get("userId");
        List<StudentGetRes> result = userService.studentInfoList(userId);
        return new ResultResponse<>("학생정보", result);
    }
    // 통신용
    @PostMapping("/professor")
    public ResultResponse<?> getProInfo(@RequestBody Map<String, List<Long>> request){
        List<Long> userId = request.get("userId");
        List<ProGetRes> result = userService.ProInfoList(userId);
        return new ResultResponse<>("교수정보" , result);
    }

    // 유저 프로필
    @GetMapping("/profile")
    public ResultResponse<?> getUserInfo(@AuthenticationPrincipal SignedUser signedUserId){
        UserGetRes result = userService.userInfoDto(signedUserId);

        return new ResultResponse<>("유저정보", result.getLoginId() == null ? "해당사항없음" : result);
    }


}
