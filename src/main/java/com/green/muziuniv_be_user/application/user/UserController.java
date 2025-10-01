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

    // 통신용(출석, 성적 학생 정보 조회)
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

        Map<Long, UserInfoGetDto> result = userService.UserInfoList(userId);
        return new ResultResponse<>("유저목록" , result);
    }
    //통신용
    @GetMapping("/dept/code")
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

    // 유저 상태 변경 (휴학/복학/휴직/복직)
    @PutMapping("/status")
    public ResponseEntity<?> updateUserStatus(@RequestParam Long userId,
                                              @RequestParam String status) {
        userService.updateUserStatus(userId, status);
        return ResponseEntity.ok("상태 변경 완료");
    }

    @GetMapping("/{userId}")
    public ResultResponse<UserInfoGetDto> getUserById(@PathVariable Long userId) {
        UserInfoGetDto dto = userService.findUserById(userId);
        if (dto == null) {
            return new ResultResponse<>("유저가 존재하지 않습니다.", null);
        }
        return new ResultResponse<>("단일 유저 조회 성공", dto);
    }


    @PostMapping("/profile")
    public ResultResponse<?> postProfilePic (@AuthenticationPrincipal SignedUser signedUserId
            , @RequestPart MultipartFile pic) {

        String savedFileName = userService.postProfilePic(signedUserId.signedUserId, pic);
        return new ResultResponse<>("프로파일 사진 등록 완료", savedFileName);
    }

    @PatchMapping("/profile")
    public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal SignedUser signedUserId
            , @RequestPart MultipartFile pic) {
        String savedFileName = userService.patchProfilePic(signedUserId.signedUserId, pic);
        return new ResultResponse<>("프로파일 사진 수정 완료", savedFileName);
    }

    @DeleteMapping("/profile")
    public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal SignedUser signedUserId) {
        userService.deleteProfilePic(signedUserId.signedUserId);
        return new ResultResponse<>("프로파일 사진 삭제 완료", null);
    }

}
