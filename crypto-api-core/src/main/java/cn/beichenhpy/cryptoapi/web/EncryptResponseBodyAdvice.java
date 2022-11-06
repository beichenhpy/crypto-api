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

package cn.beichenhpy.cryptoapi.web;

import cn.beichenhpy.cryptoapi.CryptoApiHelper;
import cn.beichenhpy.cryptoapi.CryptoType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *   针对@ResponseBody并且在配置文件了对应url以及aesKey的进行加密，需要根据返回值类型定制
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 21:13
 */
@Slf4j
@ControllerAdvice
public class EncryptResponseBodyAdvice<T> implements ResponseBodyAdvice<T> {

    @Resource
    private HttpServletRequest request;

    @Resource
    private CryptoApiHelper cryptoApiHelper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return cryptoApiHelper.getHandlerKeyOrNull(request.getServletPath(), CryptoType.ENCRYPT) != null;
    }


    @Override
    public T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse response) {
        if (body != null) {
            body = cryptoApiHelper.getEncryptHandler(request.getServletPath()).encrypt(body, returnType, selectedContentType, selectedConverterType, serverHttpRequest, response);
        }
        return body;
    }

}
