package com.fastcampus.boardserver.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class SHA256Util {
    public static final String ENCRYPTION_KEY = "SHA-256";
    public static String encryptSHA256(String str) {
        String SHA = null;

        try {
            MessageDigest sh = MessageDigest.getInstance(ENCRYPTION_KEY);
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();

        } catch (Exception e) {
            log.error("encryptSHA256 error", e);
            SHA = null;
        }

        return SHA;
    }

}
