package com.green.muziuniv_be_user.common.enumcode;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EnumMapperType {
    String getCode();
    @JsonValue
    String getValue();
}
