package com.example.balance.repositories;

import com.example.balance.entities.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findBalanceByUserid(Long userId);
}