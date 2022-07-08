package cn.beichenhpy.cryptoapi.web;

import cn.beichenhpy.cryptoapi.filter.DecryptParameterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author beichenhpy
 * <p> 2022/7/7 21:54
 */
@Configuration
public class CryptoApiAutoConfiguration<T> {


    @Bean
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice() {
        return new DecryptRequestBodyAdvice();
    }

    @Bean
    public EncryptResponseBodyAdvice<T> encryptResponseBodyAdvice() {
        return new EncryptResponseBodyAdvice<>();
    }


    @Bean
    public DecryptParameterFilter decryptParameterFilter() {
        return new DecryptParameterFilter();
    }
}
