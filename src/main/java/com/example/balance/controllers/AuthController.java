package com.example.balance.controllers;

import com.example.balance.entities.User;
import com.example.balance.message.request.LoginForm;
import com.example.balance.services.BalanceService;
import com.example.balance.services.UserService;
import com.example.balance.utils.Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

@RestController
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    BalanceService balanceService;
    @Autowired
    Digest digest;

    private final int NORM = 0;
    private final int REPEAT = 1;
    private final int WAIT = 2;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginForm loginForm ) {
        User user = userService.findUserByUsername(loginForm.getUsername());
        if (user != null) {
            return ResponseEntity.status(400).body("User exist now");
        }
        user = userService.createUser(loginForm);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm ) {
        User user = userService.findUserByUsername(loginForm.getUsername());
        LocalDateTime now = LocalDateTime.now();
        if(  user.getState() != null && user.getState() == WAIT) {
            System.out.println("user    time:" + user.getNext_date().getTime()/1000);
            System.out.println("current time:" +  new Date().getTime()/1000);
            System.out.println("==" + (user.getNext_date().getTime() > new Date().getTime()));
        }
        // пользователь не найден или не истекло время блокоровки после истечения попыток войти с енправильным паролем
        if ((user == null) || (( user.getState() != null && user.getState() == WAIT  &&
                                 user.getNext_date().getTime() > new Date().getTime()) )) {
            return ResponseEntity.status(401).body("Bad user or was to many attempt");
        }
        if (user.getPassword().equals(digest.calcDigest(loginForm.getPassword()))) {
            user = userService.loginUser(user);
// устанавливаем баланс
            balanceService.setStartBalance(user, 8.00);
            user.setPassword(null);
            return ResponseEntity.ok(user);
        }
        else {
            userService.foundInvalidPassword(user);
            return ResponseEntity.status(401).body("No authorization");
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout(@Valid @RequestHeader String token, @Valid @RequestBody LoginForm loginForm ) {

        if (token == null) {
            return ResponseEntity.status(401).body("No authorization");
        }
        User user = userService.findUserByUsername(loginForm.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body("No authorization");
        }

        if(userService.logoutUser(user, token))
            return ResponseEntity.ok("Logout");
        else
            return ResponseEntity.status(401).body("No authorization");
    }


}
