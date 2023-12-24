package com.locker.service;

import com.locker.dto.UserDto;
import com.locker.entity.User;
import com.locker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findAllUsers() throws Exception {
        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user: users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getPhone());
            userDto.setIdCard(user.getIdCard());
            userDtos.add(userDto);
        }

        return userDtos;
    }
    public UserDto findByIdCard(String idCard) throws Exception {
        Optional<User> optionalUser = this.userRepository.findByIdCard(idCard);
        if (optionalUser.isEmpty()) {
            throw new Exception("User already exists");
        }
        User user = optionalUser.get();

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setIdCard(user.getIdCard());
        return userDto;
    }

    public UserDto register(UserDto userDto) throws Exception {
        Optional<User> userOptional = this.userRepository.findByIdCard(userDto.getIdCard());
        if (userOptional.isPresent()) {
            throw new Exception("User already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setIdCard(userDto.getIdCard());
        user.setEmail(userDto.getEmail());
        User savedUser = this.userRepository.save(user);

        userDto.setId(savedUser.getId());
        return userDto;
    }

    public void update(UserDto userDto) throws Exception {
        Optional<User> userOptional = this.userRepository.findByIdCard(userDto.getIdCard());
        if (userOptional.isEmpty()) {
            throw new Exception("User does not exist");
        }

        User user = userOptional.get();
        if (Objects.nonNull(userDto.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
        if (Objects.nonNull(userDto.getPhone())) {
            user.setPhone(userDto.getPhone());
        }
        if (Objects.nonNull(userDto.getIdCard())) {
            user.setIdCard(userDto.getIdCard());
        }
        userRepository.save(user);
    }
}
