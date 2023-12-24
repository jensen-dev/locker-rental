package com.locker.controller;

import com.locker.dto.CreateRentalDto;
import com.locker.dto.OpenLockerDto;
import com.locker.dto.RentalDto;
import com.locker.dto.UserDto;
import com.locker.service.RentalService;
import com.locker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rental")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/{id}")
    public ResponseEntity findAllByUserId(@PathVariable Long id) throws Exception {
        List<RentalDto> rentalDtos = this.rentalService.findAllByUserId(id);
        return ResponseEntity.ok(rentalDtos);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CreateRentalDto createRentalDto) throws Exception {
        CreateRentalDto dto;
        try {
            dto = rentalService.create(createRentalDto);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping("/open")
    public ResponseEntity open(@RequestBody OpenLockerDto openLockerDto) throws Exception {
        Boolean openSuccessful;
        try {
            openSuccessful = rentalService.open(openLockerDto);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok(openSuccessful);
    }

    @PostMapping("/return")
    public ResponseEntity returnRental(@RequestBody CreateRentalDto createRentalDto) throws Exception {
        Boolean success;
        try {
            success = rentalService.returnRental(createRentalDto);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok(success);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody OpenLockerDto openLockerDto) throws Exception {
        Boolean success;
        try {
            success = rentalService.resetPassword(openLockerDto);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok(success);
    }
}
