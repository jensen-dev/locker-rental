package com.locker.controller;

import com.locker.dto.NotificationDto;
import com.locker.dto.UserDto;
import com.locker.service.NotificationService;
import com.locker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody NotificationDto notificationDto) throws Exception {
        notificationService.resetPassword(notificationDto);
        return ResponseEntity.ok().build();
    }
}
