package cn.beichenhpy.encryptdecryptapisample;

import cn.beichenhpy.encryptdecryptapisample.util.AES;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class EncryptDecryptApiSampleApplicationTests {

    @Value("${aes.key}")
    private String aesKey;

    @Test
    void aesEncrypt() {
        String s = AES.generateRandomKey();
        log.info(s);
    }

    @Test
    void encryptParam() throws Exception {
        String username = "han.xin";//E16GSt0rhSdOS8ueF5Ak5g==
        int age = 14;//SJDidO0vAHg2X/gU02J4LQ==
        String encryptUsername = AES.encrypt(username, aesKey);
        log.info("username: {}", encryptUsername);
        String encryptAge = AES.encrypt(Integer.toString(age), aesKey);
        log.info("age: {}", encryptAge);
    }

    @Test
    void decryptParam() throws Exception {
        String username = "E16GSt0rhSdOS8ueF5Ak5g==";
        String decrypt = AES.decrypt(username, aesKey);
        log.info(decrypt);
    }
}
