package cn.beichenhpy.cryptoapi;

import cn.beichenhpy.cryptoapi.extension.aes.AesDecryptRequestHandler;
import cn.beichenhpy.cryptoapi.extension.aes.AesEncryptResponseHandler;
import cn.beichenhpy.cryptoapi.extension.aes.config.AesCryptoApiProperties;
import cn.beichenhpy.cryptoapi.extension.util.AesCryptoHelper;
import cn.beichenhpy.cryptoapi.web.DecryptRequestBodyAdvice;
import cn.beichenhpy.cryptoapi.web.EncryptResponseBodyAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author beichenhpy
 * <p> 2022/7/7 21:54
 */
@Configuration
public class CryptoApiAutoConfiguration<T> {

    @Bean
    public CryptoApiHelper cryptoApiHelper() {
        return new CryptoApiHelper();
    }

    @Bean
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice() {
        return new DecryptRequestBodyAdvice();
    }

    @Bean
    public EncryptResponseBodyAdvice<T> encryptResponseBodyAdvice() {
        return new EncryptResponseBodyAdvice<>();
    }


    @Bean
    @ConfigurationProperties(prefix = "crypto-api.extension.aes")
    public AesCryptoApiProperties aesCryptoApiProperties() {
        return new AesCryptoApiProperties();
    }

    @Bean
    @ConditionalOnBean(value = AesCryptoApiProperties.class)
    public AesCryptoHelper aesCryptoHelper(AesCryptoApiProperties aesCryptoApiProperties) {
        return new AesCryptoHelper(aesCryptoApiProperties);
    }

    @Bean
    @ConditionalOnBean(value = AesCryptoHelper.class)
    public AesDecryptRequestHandler aesDecryptRequestHandler(AesCryptoHelper aesCryptoHelper) {
        return new AesDecryptRequestHandler(aesCryptoHelper);
    }

    @Bean
    @ConditionalOnBean(value = AesCryptoHelper.class)
    public AesEncryptResponseHandler aesEncryptResponseHandler(AesCryptoHelper aesCryptoHelper, ObjectMapper objectMapper) {
        return new AesEncryptResponseHandler(objectMapper, aesCryptoHelper);
    }

}
