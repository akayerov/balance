package com.example.balance.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class Digest {
    public String calcDigest(String text) {
        StringBuffer code = new StringBuffer(); //the hash code
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte bytes[] = text.getBytes();
        byte digest[] = messageDigest.digest(bytes); //create code
        for (int i = 0; i < digest.length; ++i) {
            code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
        }
        return(code.toString());
    }

    public String generateTokent() {
        return generateString(new Random(), "1234567890ASDFGHJKLL:{POIUYTREWQ)ZXCVBNM<>",16);
    }

    private String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
