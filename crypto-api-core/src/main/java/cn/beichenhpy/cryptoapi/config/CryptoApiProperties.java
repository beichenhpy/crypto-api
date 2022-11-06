/*
 * Copyright 2022 韩鹏宇
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.beichenhpy.cryptoapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author beichenhpy
 * <p> 2022/5/2 21:03
 */
@Data
@ConfigurationProperties(prefix = "crypto-api")
public class CryptoApiProperties {


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
         * 用于加密解密的handler的key
         */
        private String handlerKey;

        /**
         * 对应的请求路径 支持*通配符
         */
        private List<String> paths = new ArrayList<>();
    }

}
