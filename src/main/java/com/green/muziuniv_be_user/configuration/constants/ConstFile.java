package com.green.muziuniv_be_user.configuration.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "constants.file")
@RequiredArgsConstructor
@ToString
public class ConstFile {
    private final String directory;
    private final String feedPic;
    private final String profilePic;
}
