package com.green.muziuniv_be_user.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //auditing 기능 활성화
@ConfigurationPropertiesScan
public class AnnotationConfiguration {}
