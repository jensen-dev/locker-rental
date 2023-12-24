package com.locker.controller;

import com.locker.dto.UserDto;
import com.locker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity getAllUsers() throws Exception {
        List<UserDto> userDtos = this.userService.findAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{idCard}")
    public ResponseEntity getUser(@PathVariable String idCard) throws Exception {
        UserDto userDto = this.userService.findByIdCard(idCard);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDto) throws Exception {
        UserDto dto = userService.register(userDto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody UserDto userDto) throws Exception {
        userService.update(userDto);
        return ResponseEntity.ok().build();
    }
}
