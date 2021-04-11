package com.example.balance.services;

import com.example.balance.entities.Balance;
import com.example.balance.entities.User;
import com.example.balance.repositories.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by AndreyK on 24.12.2020.
 */
@Service
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setStartBalance(User user, double value) {
        Balance balance = balanceRepository.findBalanceByUserid(user.getId());
        if(balance == null) {
            balance = new Balance();
            balance.setUserid(user.getId());
        }
        balance.setBalance(value);
        em.persist(balance);

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void decriseBalance(long userId) {
        Balance balance = balanceRepository.findBalanceByUserid(userId);
        if( balance != null) {
            balance.setBalance(balance.getBalance() - 1.11);
            em.persist(balance);
        }
    }

}
