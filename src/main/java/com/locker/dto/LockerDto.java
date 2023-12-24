package com.locker.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LockerDto {
    private Long id;
    private String name;
    private Boolean isAvailable;
}
