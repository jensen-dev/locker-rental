package com.locker.service;

import com.locker.dto.NotificationDto;
import com.locker.repository.NotificationRepository;
import com.locker.util.NotificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class NotificationService {
    @Autowired
    private NotificationUtil notificationUtil;

    public void sendEmail(NotificationDto notificationDto) {
        this.notificationUtil.sendEmail(notificationDto);
    }
    public void resetPassword(NotificationDto notificationDto) {
        Random rnd = new Random();
        String password = String.format("%06d", rnd.nextInt(999999));
        notificationDto.setPassword(password);
        this.notificationUtil.sendEmail(notificationDto);
    }
}
