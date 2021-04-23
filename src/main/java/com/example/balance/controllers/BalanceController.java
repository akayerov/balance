package com.example.balance.controllers;

import com.example.balance.annotation.TestAnnotation;
import com.example.balance.entities.User;
import com.example.balance.services.BalanceService;
import com.example.balance.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Method;


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

	@PostMapping("/credit2")
	public ResponseEntity<?> credit(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getAttribute("user");
		System.out.println("user = " + user);

		if(user != null) {
			balanceService.decriseBalance(user.getId());
			return ResponseEntity.ok("Success");
		}
		else
			return  ResponseEntity.status(401).body("No authorization");
	}

// Использовать аннотацию в коде метода довальрно бессмысленно - в сумме получается такое же объем кода
// но если перенести эти вычисления в Fiter or Inteceptor - то это становится интереснее
// тогда этот код был бы одним на все метод
// проблема на этапе выполнения interseptor надо знать метод который будет вызываться
// т.е разобрать url
// ЭТО ДЕЛАЕТСЯ С помощью интрецепторов! см. CustomHandlerIntercepter
    @TestAnnotation( role = "ADMIN")
	@PostMapping("/credit3")
	public ResponseEntity<?> credit3(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getAttribute("user");
		System.out.println("user = " + user);

		for (Method m : this.getClass().getDeclaredMethods())
		{
			if (m.isAnnotationPresent(TestAnnotation.class))
			{
				System.out.println("annotation enable " + m.getName());
				TestAnnotation anno = m.getAnnotation(TestAnnotation.class);
				System.out.println("annotation value " + anno.role());
			}
		}

		if(user != null) {
			balanceService.decriseBalance(user.getId());
			return ResponseEntity.ok("Success");
		}
		else
			return  ResponseEntity.status(401).body("No authorization");
	}

	// ЭТО ДЕЛАЕТСЯ С помощью интрецепторов! см. CustomHandlerIntercepter
	@TestAnnotation( role = "ADMIN")
	@PostMapping("/credit4")
	public ResponseEntity<?> credit4(ServletRequest request) {
// в общем случае user здесь не нужен дял проверки достпупа  решаются в фильтрах и интерцепторах
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getAttribute("user");
		System.out.println("user = " + user);

		balanceService.decriseBalance(user.getId());
		return ResponseEntity.ok("Success");
	}
}