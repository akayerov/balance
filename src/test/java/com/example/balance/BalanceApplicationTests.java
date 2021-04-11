package com.example.balance;

import com.example.balance.controllers.BalanceController;
import com.example.balance.utils.Aes256;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

//@SpringBootTest
class BalanceApplicationTests {
    @Autowired
    BalanceController balanceController;
    @Test
    void contextLoads() {

    }

    @Test
    void textEncoder() {
  /*
        StringBuffer code = new StringBuffer(); //the hash code
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String text = "Hello Andrey";
        byte bytes[] = text.getBytes();
        byte digest[] = messageDigest.digest(bytes); //create code
        for (int i = 0; i < digest.length; ++i) {
            code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
        }

        String md5 = code.toString();
        System.out.println(md5.toString());
*/
    }

}
