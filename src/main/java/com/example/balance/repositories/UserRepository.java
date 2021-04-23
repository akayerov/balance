package com.example.balance.repositories;

import com.example.balance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findUserByToken(String token);
    User findUserById(long id);
}