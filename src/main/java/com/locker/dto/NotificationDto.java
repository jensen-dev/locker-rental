package com.locker.dto;

import com.locker.entity.Notification;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString
public class NotificationDto {
    private Long id;
    private String password;
    private String idCard;
    private String email;
}
