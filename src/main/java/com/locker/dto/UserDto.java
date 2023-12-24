package com.locker.dto;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString
public class UserDto {
    private Long id;
    private String name;
    private String phone;
    private String idCard;
    private String email;
}
