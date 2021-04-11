package com.example.balance.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
@Component
public class Aes256 {
    private SecretKey secretKey;
    public Aes256() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            this.secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public byte[] makeAes(byte[] rawMessage, int cipherMode){
        try {
            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(cipherMode, this.secretKey);
            cipher.init(cipherMode, new Key() {
                @Override
                public String getAlgorithm() {
                    return null;
                }

                @Override
                public String getFormat() {
                    return null;
                }

                @Override
                public byte[] getEncoded() {
                    return new byte[0];
                }
            });
            byte [] output = cipher.doFinal(rawMessage);
            return output;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}