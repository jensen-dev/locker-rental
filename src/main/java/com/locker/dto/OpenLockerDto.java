package com.locker.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OpenLockerDto {
    private Long renterId;
    private Long lockerId;
    private String name;
    private String password;
}
