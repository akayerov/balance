package com.example.balance.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false)
    private Long userid;

    private double balance;

}
// f84aa644af6dc204e5a12c671c75d98d
// f84aa644af6dc204e5a12c671c75d98d