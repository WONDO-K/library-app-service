package com.group.libraryapp.dto.user.request;

import lombok.Getter;

@Getter
public class UserCreateRequest {
    private String name;
    private Integer age; // Integer는 null을 표현할 수 있다.

}
