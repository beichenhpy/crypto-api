package cn.beichenhpy.encryptdecryptapisample.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 21:03
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "encrypt-decrypt-api")
public class EncryptDecryptApiProperties {


    private String aesKey;

    private List<String> decryptUrls;
}
