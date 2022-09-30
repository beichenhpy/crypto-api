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

import cn.beichenhpy.cryptoapi.util.CryptoApiHelper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  crypto-api:
 *   apis:
 *     demo1:
 *       paths:
 *         - /demo/*
 *         - /demo/query
 *       aesKey: f5d830d77163a58f
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 21:03
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "crypto-api")
public class CryptoApiProperties {


    private Map<String, CryptoPath> apis = new LinkedHashMap<>();


    @Data
    public static class CryptoPath {

        private String aesKey;

        private List<String> paths = new ArrayList<>();
    }


    @Bean
    public CryptoApiProperties cryptoApiProperties() {
        return new CryptoApiProperties();
    }

    @Bean
    public CryptoApiHelper cryptoApiHelper() {
        return new CryptoApiHelper(cryptoApiProperties());
    }
}
