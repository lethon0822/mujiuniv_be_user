package com.green.muziuniv_be_user.application.user;


import com.green.muziuniv_be_user.application.user.model.ProGetRes;
import com.green.muziuniv_be_user.application.user.model.StudentGetRes;
import com.green.muziuniv_be_user.common.model.ResultResponse;
import com.green.muziuniv_be_user.entity.professor.Professor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/student")
    public ResultResponse<?> getStudentInfo(@RequestBody Map<String, List<Long>> request) {
        List<Long> userId = request.get("userId");
        List<StudentGetRes> result = userService.studentInfoList(userId);
        return new ResultResponse<>("susu", result);
    }

    @PostMapping("/professor")
    public ResultResponse<?> getProInfo(@RequestBody Map<String, List<Long>> request){
        List<Long> userId = request.get("userId");
        List<ProGetRes> result = userService.ProInfoList(userId);
        return new ResultResponse<>("교수정보" , result);
    }

}
