package com.locker.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class CreateRentalDto {
    private String renterIdCard;
    private List<Long> lockerIds;
}
