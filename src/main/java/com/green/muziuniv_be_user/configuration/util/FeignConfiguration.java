package com.green.muziuniv_be_user.configuration.util;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// 디버깅용 요청, 응답시 로그를 모두 찍는다

@Configuration
public class FeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel(){ return Logger.Level.FULL;}
}
