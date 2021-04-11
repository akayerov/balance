package com.example.balance.controllers;

import com.example.balance.entities.User;
import com.example.balance.services.BalanceService;
import com.example.balance.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class BalanceController {
	private static final Logger LOG = LoggerFactory.getLogger(BalanceController.class);

	@Autowired
	BalanceService balanceService;
	@Autowired
	UserService userService;

	@PostMapping("/credit")
	public ResponseEntity<?> credit(@Valid @RequestHeader String token) {
		User user = userService.findUserByToken(token);
		if(user != null) {
			balanceService.decriseBalance(user.getId());
			return ResponseEntity.ok("Success");
		}
		else
		    return  ResponseEntity.status(401).body("No authorization");
	}



}