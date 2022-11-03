package cn.beichenhpy.cryptoapi.config;

import cn.beichenhpy.cryptoapi.filter.DecryptParameterFilter;
import cn.beichenhpy.cryptoapi.util.CryptoApiHelper;
import cn.beichenhpy.cryptoapi.web.DecryptRequestBodyAdvice;
import cn.beichenhpy.cryptoapi.web.EncryptResponseBodyAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author beichenhpy
 * <p> 2022/7/7 21:54
 */
@Configuration
public class CryptoApiAutoConfiguration<T> {

    @Bean
    public CryptoApiProperties cryptoApiProperties() {
        return new CryptoApiProperties();
    }

    @Bean
    @ConditionalOnBean(value = CryptoApiProperties.class)
    public CryptoApiHelper cryptoApiHelper() {
        return new CryptoApiHelper(cryptoApiProperties());
    }

    @Bean
    @ConditionalOnProperty(prefix = "crypto-api.decrypt", value = "enable", havingValue = "true")
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice() {
        return new DecryptRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnProperty(prefix = "crypto-api.decrypt", value = "enable", havingValue = "true")
    public DecryptParameterFilter decryptParameterFilter() {
        return new DecryptParameterFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "crypto-api.encrypt", value = "enable", havingValue = "true")
    public EncryptResponseBodyAdvice<T> encryptResponseBodyAdvice() {
        return new EncryptResponseBodyAdvice<>();
    }


}
