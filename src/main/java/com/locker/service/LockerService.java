package com.locker.service;

import com.locker.dto.CreateRentalDto;
import com.locker.dto.LockerDto;
import com.locker.dto.OpenLockerDto;
import com.locker.dto.UserDto;
import com.locker.entity.Locker;
import com.locker.entity.Rental;
import com.locker.entity.User;
import com.locker.repository.LockerRepository;
import com.locker.repository.RentalRepository;
import com.locker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LockerService {
    @Autowired
    private LockerRepository lockerRepository;
    @Autowired
    private RentalRepository rentalRepository;

    public List<LockerDto> findAllLockers() {
        List<Locker> lockers = this.lockerRepository.findAll();
        List<LockerDto> lockerDtos = new ArrayList<>();

        for (Locker locker: lockers) {
            LockerDto lockerDto = new LockerDto();
            lockerDto.setId(locker.getId());
            lockerDto.setName(locker.getName());
            lockerDtos.add(lockerDto);
        }
        return lockerDtos;
    }

    public LockerDto findByName(String name) {
        Optional<Locker> optionalLocker = this.lockerRepository.findByName(name);
        if (optionalLocker.isEmpty()) {
            return null;
        }
        Locker locker = optionalLocker.get();

        LockerDto lockerDto = new LockerDto();
        lockerDto.setId(locker.getId());
        lockerDto.setName(locker.getName());
        return lockerDto;
    }

    public LockerDto create(LockerDto lockerDto) throws Exception {
        Optional<Locker> lockerOptional = this.lockerRepository.findByName(lockerDto.getName());
        if (lockerOptional.isPresent()) {
            throw new Exception("Locker already exists");
        }

        Locker locker = new Locker();
        locker.setName(lockerDto.getName());
        locker.setIsAvailable(true);
        Locker lockerSaved = this.lockerRepository.save(locker);

        lockerDto.setId(lockerSaved.getId());
        lockerDto.setIsAvailable(true);
        return lockerDto;
    }

    public void update(LockerDto lockerDto) throws Exception {
        Optional<Locker> lockerOptional = this.lockerRepository.findById(lockerDto.getId());
        if (lockerOptional.isEmpty()) {
            throw new Exception("Locker does not exist");
        }

        Locker locker = lockerOptional.get();
        if (Objects.nonNull(lockerDto.getName())) {
            locker.setName(lockerDto.getName());
        }
        if (Objects.nonNull(lockerDto.getIsAvailable())) {
            locker.setIsAvailable(lockerDto.getIsAvailable());
        }
        lockerRepository.save(locker);
    }

    public void delete(String name) throws Exception {
        Optional<Locker> lockerOptional = this.lockerRepository.findByName(name);
        if (lockerOptional.isEmpty()) {
            throw new Exception("Locker does not exist");
        }

        this.lockerRepository.delete(lockerOptional.get());
    }
}
