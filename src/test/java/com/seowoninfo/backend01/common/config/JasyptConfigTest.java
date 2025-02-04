package com.seowoninfo.backend01.common.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-02-04
 * Description :
 */
class JasyptConfigTest {
    @Test
    @DisplayName("비밀번호 암호화")
    public void passwordEncode(){
        String url = "jdbc:mariadb://61.251.164.178:3306/seowon";
        String username = "seowon";
        String password = "seowon";
        String secret = "ThisIsSeowonFrameworkAndThisFrameworkIsBorn20250101ByDev3";

        System.out.println("url =" + jasyptEncoding(url));
        System.out.println("username =" + jasyptEncoding(username));
        System.out.println("password =" + jasyptEncoding(password));
        System.out.println("secret =" + jasyptEncoding(secret));
    }

    public String jasyptEncoding(String value) {

        String key = "seowon";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}