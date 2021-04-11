package com.example.balance.services;

import com.example.balance.entities.User;
import com.example.balance.message.request.LoginForm;
import com.example.balance.repositories.UserRepository;
import com.example.balance.utils.Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by AndreyK on 24.12.2020.
 */
@Service
public class UserService {
    private static final Integer MAX_NUMBER_ATTEMPT = 3;  // масимальное число попыток после чего, будет пауза в DELAY секунд
    private static final Integer DELAY = 60 * 1000;
    private static final int NORM = 0;
    private static final int REPEAT = 1;
    private static final int WAIT = 2;

    @Autowired
    UserRepository userRepository;
    @Autowired
    Digest digest;

    @PersistenceContext
    private EntityManager em;


    public User findUserByUsername(String userName) {
       return userRepository.findByUsername(userName);
    }
    public User findUserByToken(String token) {
        return userRepository.findUserByToken(token);
    }


    @Transactional
    public User createUser(@Valid LoginForm loginForm) {
        User user = new User();
        user.setUsername(loginForm.getUsername());
        user.setPassword(digest.calcDigest(loginForm.getPassword()));
        user.setToken(generateUniqueToken());
        em.persist(user);
       return user;
    }

    @Transactional
    public boolean logoutUser(User user, String token) {
        if (token != null && token.equals(user.getToken())) {
            user.setToken(null);
            em.persist(user);
            return true;
        }
        else
            return false;
    }

    @Transactional
    public User loginUser(User user) {
        user.setToken(generateUniqueToken());

        user.setAttempt_count(0);
        user.setNext_date(null);
        user.setState(NORM);

        em.persist(user);
        return user;
    }

    @Transactional
    // изменения состояния при получении неправильного пароля
    public User foundInvalidPassword(User user) {
        if( user.getState() == NORM ) {
            user.setState(REPEAT);
            user.setAttempt_count(1);
            em.persist( user );
        }
        else if( user.getState() == REPEAT) {
            if( user.getAttempt_count() >= MAX_NUMBER_ATTEMPT - 1) {
               user.setState( WAIT);
               Date d = new Date();
               long msec = d.getTime() + DELAY;
               user.setNext_date(new Date(msec));
            }
            else {
                user.setAttempt_count(user.getAttempt_count() + 1);
            }
            em.persist( user );
        }
        else if( user.getState() == WAIT) {
            user.setState(REPEAT);
            user.setAttempt_count(1);
            em.persist( user );
        }
        return user;
    }
// Генерация уникальной строки токена
    private String generateUniqueToken() {
        User user;
        String token;
        do {
          token =  digest.generateTokent();
          user = userRepository.findUserByToken(token);
        } while ( user != null);
        return token;
    }

}
