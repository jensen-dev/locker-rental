
package com.locker.controller;

import com.locker.dto.CreateRentalDto;
import com.locker.dto.LockerDto;
import com.locker.dto.OpenLockerDto;
import com.locker.dto.UserDto;
import com.locker.service.LockerService;
import com.locker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locker")
public class LockerController {
    @Autowired
    private LockerService lockerService;

    @GetMapping()
    public ResponseEntity getAllLockers() {
        List<LockerDto> lockerDtos = this.lockerService.findAllLockers();
        return ResponseEntity.ok(lockerDtos);
    }

    @GetMapping("/{name}")
    public ResponseEntity getLocker(@RequestParam String name) {
        LockerDto lockerDto = this.lockerService.findByName(name);
        return ResponseEntity.ok(lockerDto);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody LockerDto lockerDto) throws Exception {
        LockerDto dto = lockerService.create(lockerDto);
        return ResponseEntity.status(200).body(dto);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody LockerDto lockerDto) throws Exception {
        lockerService.update(lockerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) throws Exception {
        lockerService.delete(name);
        return ResponseEntity.ok().build();
    }
}
