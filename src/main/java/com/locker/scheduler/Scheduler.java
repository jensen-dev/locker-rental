package com.locker.scheduler;

import com.locker.entity.Rental;
import com.locker.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    private RentalRepository rentalRepository;

    @Scheduled(cron = "0 0 0/1 * * ?")
//    @Scheduled(cron = "0 0 11-18 * * ?")
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void update() {
        List<Rental> rentals = this.rentalRepository.findAll();
        List<Rental> rentalList = new ArrayList<>();

        for (Rental rental: rentals) {
            Date rentalDate = rental.getRentalDate();
            LocalDateTime from = LocalDateTime.ofInstant(rentalDate.toInstant(), ZoneId.systemDefault());
            LocalDateTime to = LocalDateTime.now();
            Duration duration = Duration.between(from, to);
            long diffInMinutes = duration.toMinutes();
            if (diffInMinutes >= 1440L) {
                rental.setTotalAmt(rental.getTotalAmt()+5000);
                LocalDateTime tomorrow = from.plusHours(24);
                rental.setRentalDate(Date.from(tomorrow.atZone(ZoneId.systemDefault()).toInstant()));
                rentalList.add(rental);
            }
        }
        rentalRepository.saveAll(rentalList);
    }
}
