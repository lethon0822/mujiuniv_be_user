package com.green.muziuniv_be_user.configuration.enumcode.model;


import com.green.muziuniv_be_user.configuration.enumcode.AbstractEnumCodeConverter;
import com.green.muziuniv_be_user.configuration.enumcode.EnumMapperType;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum EnumUserRole implements EnumMapperType {
    STUDENT("student", "학생"),
    PROFESSOR("professor", "교수"),
    STAFF("staff", "직원")
    ;

    private final String code;
    private final String value;

    @Converter(autoApply = true)
    public static class CodeConverter extends AbstractEnumCodeConverter<EnumUserRole> {
        public CodeConverter() { super(EnumUserRole.class, false); }
    }
}
