package com.example.balance;

import com.example.balance.controllers.AuthController;
import com.example.balance.message.request.LoginForm;
import com.example.balance.services.BalanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.Assert.*;

//

@RunWith (SpringRunner.class)
@SpringBootTest
public class demoTest1 {
    @Autowired
    AuthController authController;
    @Autowired
    LoginForm loginForm;

    @Autowired
    BalanceService balanceService;

    @Test
    public void test1() {
        Object obj = null;
        //	assertNotNull("Must be not Null", obj);
        assertNull("Must be Null", obj);

    }
    @Test
    public void test2() {
        String str = "Hello word";
        assertNotNull("Must be not Null", str);
        assertEquals("Must be equals", "Hello word", str);
        assertNotEquals("Must be not equals", "Gello word", str);

    }

    @Test
    public void test3() {
        balanceService.decriseBalance(10L);
        String str = "Hello word";
        assertEquals("Must be equals", "Hello word", str);

    }





}
