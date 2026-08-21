package com.green.muziuniv_be_user.application.account.model.excelFile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FailedRow {
    private int row;
    private String message;
}
