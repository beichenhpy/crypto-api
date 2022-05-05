package cn.beichenhpy.encryptdecryptapisample;

import cn.beichenhpy.cryptoapi.util.AES;
import cn.beichenhpy.cryptoapi.web.config.CryptoApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class CryptoApiApplicationTests {

    @Resource
    private CryptoApiProperties cryptoApiProperties;

    @Test
    void aesEncrypt() {
        String s = AES.generateRandomKey();
        log.info(s);
    }

    @Test
    void encryptParam() throws Exception {
        String username = "han.xin";//E16GSt0rhSdOS8ueF5Ak5g==
        int age = 14;//SJDidO0vAHg2X/gU02J4LQ==
        String encryptUsername = AES.encrypt(username, cryptoApiProperties.getAesKey());
        log.info("username: {}", encryptUsername);
        String encryptAge = AES.encrypt(Integer.toString(age), cryptoApiProperties.getAesKey());
        log.info("age: {}", encryptAge);
    }

    @Test
    void decryptParam() throws Exception {
        String username = "E16GSt0rhSdOS8ueF5Ak5g==";
        String decrypt = AES.decrypt(username, cryptoApiProperties.getAesKey());
        log.info(decrypt);
    }


    @Test
    void decryptResult() throws Exception {
        String _result = "gCvRzKijHqYCThp59TJdNKFMzhAFJOCgOlgbsvBh/ezqnsUexhXScpfDFxZ/xLUFa+A6UemYFDYuQTqUaMr6Bx0puxgP2Ghtd5PbDSqhdJofUM36kbHBH9w99ZpKoZ76dUJInuCpox2SPEFWEx9BglCr2VlxdOdcQqaOkOC9+f029lTzFDoz8TGZIQyo8LBq";
        String decrypt = AES.decrypt(_result, cryptoApiProperties.getAesKey());
        log.info(decrypt);
    }
}
