package com.locker.service;

import com.locker.dto.CreateRentalDto;
import com.locker.dto.NotificationDto;
import com.locker.dto.OpenLockerDto;
import com.locker.dto.RentalDto;
import com.locker.entity.Locker;
import com.locker.entity.Rental;
import com.locker.entity.User;
import com.locker.repository.LockerRepository;
import com.locker.repository.RentalRepository;
import com.locker.repository.UserRepository;
import com.locker.util.NotificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LockerRepository lockerRepository;
    @Autowired
    private NotificationUtil notificationUtil;

    public List<RentalDto> findAllByUserId(Long id) {
        List<Rental> rentals = this.rentalRepository.findByUserIdAndStatus(id, "unpaid");
        List<RentalDto> rentalDtos = new ArrayList<>();

        for (Rental rental: rentals) {
            RentalDto rentalDto = new RentalDto();
            rentalDto.setId(rental.getId());
            rentalDto.setRentalDate(rental.getRentalDate());
            rentalDto.setStatus(rental.getStatus());
            rentalDto.setTotalAmt(rental.getTotalAmt());
            rentalDto.setPasswordAttempt(rental.getPasswordAttempt());
            rentalDto.setPassword(rental.getPassword());
            rentalDto.setRenterId(rental.getUser().getId());
            rentalDto.setLockerId(rental.getLocker().getId());
            rentalDtos.add(rentalDto);
        }
        return rentalDtos;
    }

    public CreateRentalDto create(CreateRentalDto createRentalDto) throws Exception {
        if (createRentalDto.getLockerIds().size() > 3) {
            throw new Exception("You can only rent up to three lockers");
        }

        Optional<User> userOptional = this.userRepository.findByIdCard(createRentalDto.getRenterIdCard());
        if (userOptional.isEmpty()) {
            throw new Exception("User is not found");
        }

        User user = userOptional.get();
        List<RentalDto> existingRentals = this.findAllByUserId(user.getId());
        if ((existingRentals.size()+createRentalDto.getLockerIds().size()) > 3) {
            throw new Exception("Number of lockers you can rent is three");
        }

        List<Locker> lockers = this.lockerRepository.findAllById(createRentalDto.getLockerIds());
        for (Locker locker: lockers) {
            if (!locker.getIsAvailable()) {
                throw new Exception("locker id " + locker.getId() + " is being rented out");
            }
        }
        if (lockers.size() != createRentalDto.getLockerIds().size()) {
            throw new Exception("One of the locker is not found");
        }

        List<Rental> rentals = new ArrayList<>();
        for (Locker locker: lockers) {
            locker.setIsAvailable(false);
            Rental rental = new Rental();
            rental.setRentalDate(new Date());
            rental.setStatus("unpaid");
            rental.setTotalAmt(10000);
            rental.setPasswordAttempt(0);
            String password = generateSixDigitsPassword();
            rental.setPassword(password);
            rental.setLocker(locker);
            rental.setUser(user);
            rental.setRefundAmt(10000);
            rentals.add(rental);

            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setEmail(user.getEmail());
            notificationDto.setPassword(password);
            notificationDto.setIdCard(user.getIdCard());
            this.notificationUtil.sendEmail(notificationDto);
            this.lockerRepository.save(locker);
        }
        this.rentalRepository.saveAll(rentals);

        return createRentalDto;
    }

    public Boolean returnRental(CreateRentalDto createRentalDto) throws Exception {
        Optional<User> userOptional = this.userRepository.findByIdCard(createRentalDto.getRenterIdCard());
        if (userOptional.isEmpty()) {
            throw new Exception("User is not found");
        }

        List<Rental> rentals = this.rentalRepository.findByUserIdAndStatus(userOptional.get().getId(), "unpaid");
        if (rentals.isEmpty()) {
            return false;
        }
        List<Locker> lockers = new ArrayList<>();
        List<Rental> rentalList = new ArrayList<>();
        for (Rental rental: rentals) {
            Optional<Locker> lockerOptional = this.lockerRepository.findById(rental.getLocker().getId());
            if (lockerOptional.isEmpty()) {
                throw new Exception("User is not found");
            }
            Locker locker = lockerOptional.get();
            locker.setIsAvailable(true);
            lockers.add(locker);

            rental.setStatus("paid");
            rentalList.add(rental);
        }
        this.lockerRepository.saveAll(lockers);

        this.rentalRepository.saveAll(rentalList);

        return true;
    }

    public Boolean open(OpenLockerDto openLockerDto) throws Exception {
        Rental rental = this.rentalRepository.findByUserIdAndLockerIdAndStatus(openLockerDto.getRenterId(), openLockerDto.getLockerId(), "unpaid");
        if (Objects.isNull(rental)) {
            throw new Exception("Rental is not found");
        }

        if (rental.getPasswordAttempt() == 3) {
            throw new Exception("Your locker has been blocked");
        }
        if (!rental.getPassword().equals(openLockerDto.getPassword())) {
            rental.setPasswordAttempt(rental.getPasswordAttempt()+1);
            if (rental.getPasswordAttempt() == 3) {
                rental.setTotalAmt(rental.getTotalAmt()+25000);
                rental.setRefundAmt(0);
            }
            rentalRepository.save(rental);
            return false;
        }

        return true;
    }

    public Boolean resetPassword(OpenLockerDto openLockerDto) throws Exception {
        Rental rental = this.rentalRepository.findByUserIdAndLockerIdAndStatusAndPasswordAttempt(
                openLockerDto.getRenterId(), openLockerDto.getLockerId(), "unpaid", 3);
        if (Objects.isNull(rental)) {
            throw new Exception("Rental is not found");
        }
        String newPassword = generateSixDigitsPassword();
        while (rental.getPassword().equals(newPassword)) {
            newPassword = generateSixDigitsPassword();
        }
        rental.setPassword(newPassword);
        rental.setPasswordAttempt(0);
        this.rentalRepository.save(rental);
        //
        Optional<User> userOptional = this.userRepository.findById(openLockerDto.getRenterId());
        if (userOptional.isEmpty()) {
            throw new Exception("User is not found");
        }
        User user = userOptional.get();
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setEmail(user.getEmail());
        notificationDto.setPassword(newPassword);
        notificationDto.setIdCard(user.getIdCard());
        this.notificationUtil.sendEmail(notificationDto);
        //
        return true;
    }

    private static String generateSixDigitsPassword() {
        Random rnd = new Random();
        String password = String.format("%06d", rnd.nextInt(999999));
        return password;
    }
}
