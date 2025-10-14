package com.green.muziuniv_be_user.application.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CountPeopleRes {
    private List<Integer> students;
    private int professor;
}
