package com.locker.repository;

import com.locker.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long id);
    Rental findByUserIdAndLockerId(Long renterid, Long lockerId);
    Rental findByUserIdAndLockerIdAndStatus(Long renterid, Long lockerId, String status);
    Rental findByUserIdAndLockerIdAndStatusAndPasswordAttempt(Long renterid, Long lockerId, String status, int passwordAttempt);
    List<Rental> findByUserIdAndStatus(Long id, String status);
}
