package com.seowoninfo.backend01.common.config;

import com.seowoninfo.backend01.common.util.UtilProperty;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-02-04
 * Description :
 */
@SpringBootTest
@TestPropertySource(properties = "classpath=application-test.yml")
class JasyptConfigTest {

    @Value("${jasypt.encryptor.key}") String key;

    @Test
    @DisplayName("비밀번호 암호화")
    public void passwordEncode(){
//        String url = "jdbc:mariadb://61.251.164.178:3306/seowon";
//        String username = "seowon";
//        String password = "seowon";
        String url = "jdbc:postgresql://ryan-seowon-db.postgres.database.azure.com:5432/postgres";
        String username = "ryan";
        String password = "1q2w#E$R";
        String secret = "ThisIsSeowonFrameworkAndThisFrameworkIsBorn20250101ByDev3";

        System.out.println("key = " + key);
        System.out.println("url =" + jasyptEncoding(url));
        System.out.println("username =" + jasyptEncoding(username));
        System.out.println("password =" + jasyptEncoding(password));
        System.out.println("secret =" + jasyptEncoding(secret));
    }

    public String jasyptEncoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}