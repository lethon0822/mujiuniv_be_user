package com.green.muziuniv_be_user.openfeign.semester;


import com.green.muziuniv_be_user.common.util.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${constants.open-feign.function}", configuration = FeignConfiguration.class)
public interface SemesterClient {
    @GetMapping("/api/semester")
    int getSemesterId(@RequestParam(name = "year")int year
            , @RequestParam(name = "semester") int semester );
}
