package com.locker.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class RentalDto {
    private Long id;
    private int passwordAttempt;
    private int totalAmt;
    private String status;
    private String password;
    private Date rentalDate;
    private Long renterId;
    private Long lockerId;
}
