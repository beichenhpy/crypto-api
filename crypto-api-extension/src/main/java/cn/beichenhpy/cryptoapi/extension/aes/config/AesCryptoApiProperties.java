package cn.beichenhpy.cryptoapi.extension.aes.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author beichenhpy
 * <p> 2022/11/6 16:14
 */
@Data
public class AesCryptoApiProperties {


    /**
     * 加密
     */
    private EncryptApi encrypt;

    /**
     * 解密
     */
    private DecryptApi decrypt;


    @Data
    public static class EncryptApi {

        /**
         * 是否启用
         */
        private boolean enable = true;

        /**
         * aesKey及请求路径
         */
        private Map<String, CryptoPath> apis = new LinkedHashMap<>();
    }

    @Data
    public static class DecryptApi {
        /**
         * 是否启用
         */
        private boolean enable = true;

        /**
         * aesKey及请求路径
         */
        private Map<String, CryptoPath> apis = new LinkedHashMap<>();
    }

    @Data
    public static class CryptoPath {

        /**
         * 用于加密解密的aesKey
         */
        private String aesKey;

        /**
         * 对应的请求路径 支持*通配符
         */
        private List<String> paths = new ArrayList<>();
    }

}
