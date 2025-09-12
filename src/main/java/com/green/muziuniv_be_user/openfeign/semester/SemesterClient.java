package com.green.muziuniv_be_user.openfeign.semester;


import com.green.muziuniv_be_user.configuration.util.FeignConfiguration;
import com.green.muziuniv_be_user.openfeign.semester.model.SemesterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${constants.open-feign.function}", configuration = FeignConfiguration.class)
public interface SemesterClient {
    @GetMapping("/api/semester/find")
    SemesterDto getSemesterId(@RequestParam(name = "year")Integer year
            , @RequestParam(name = "semester") Integer semester );
}
