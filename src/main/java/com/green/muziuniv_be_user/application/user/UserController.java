package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.MemberGetReq;
import com.green.muziuniv_be_user.application.user.model.UserInfoGetDto;
import com.green.muziuniv_be_user.application.user.model.StudentGetDto;
import com.green.muziuniv_be_user.application.user.model.UserGetRes;
import com.green.muziuniv_be_user.configuration.model.ResultResponse;
import com.green.muziuniv_be_user.configuration.model.SignedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        List<StudentGetDto> result = userService.studentInfoList(userId);
        return new ResultResponse<>("학생정보", result);
    }
    // 통신용
    @PostMapping("/list")
    public ResultResponse<?> getProInfo(@RequestBody Map<String, List<Long>> request){
        List<Long> userId = request.get("userId");
        if(userId == null){
            return new ResultResponse<>("유저목록이 존재하지 않습니다", null);
        }

        List<UserInfoGetDto> result = userService.UserInfoList(userId);
        return new ResultResponse<>("유저목록" , result);
    }
    //통신용
    @GetMapping("/dept")
    public ResponseEntity<?> getProDept(@RequestParam("user_id") Long userId){
        String  result = userService.ProDeptCode(userId);
        return ResponseEntity.ok(result);
    }


    // 유저 프로필
    @GetMapping("/profile")
    public ResultResponse<?> getUserInfo(@AuthenticationPrincipal SignedUser signedUserId){
        UserGetRes result = userService.userInfoDto(signedUserId);

        return new ResultResponse<>("유저정보", result.getLoginId() == null ? "해당사항없음" : result);
    }

    // 유저 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> getMember(@ModelAttribute MemberGetReq req) {
        return ResponseEntity.ok(userService.findUser(req));
    }

    //-------------------------------------------------------------------------


    //이거 필요
    @PatchMapping("/profile/pic")
    public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal SignedUser signedUserId
            , @RequestPart MultipartFile pic) {
        String savedFileName = userService.patchProfilePic(signedUserId.signedUserId, pic);
        return new ResultResponse<>("프로파일 사진 수정 완료", savedFileName);
    }

    //DeleteMapping - /profile/pic
    //프로파일 있는 폴더를 삭제하고
    //return new ResultResponse<>("프로파일 사진 삭제 완료", null);

    @DeleteMapping("/profile/pic")
    public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal SignedUser signedUserId) {
        userService.deleteProfilePic(signedUserId.signedUserId);
        return new ResultResponse<>("프로파일 사진 삭제 완료", null);
    }
}
