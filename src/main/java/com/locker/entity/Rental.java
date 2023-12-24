package com.locker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rental")
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "locker_id")
    Locker locker;
    @Column(name = "password_attempt")
    private int passwordAttempt;
    @Column(name = "total_amt")
    private int totalAmt;
    @Column(name = "status")
    private String status;
    @Column(name = "password")
    private String password;
    @Column(name = "rental_date")
    private Date rentalDate;
    @Column(name = "refund_amt")
    private int refundAmt;
}
