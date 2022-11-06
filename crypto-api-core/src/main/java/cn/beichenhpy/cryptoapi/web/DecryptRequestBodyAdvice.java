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

import cn.beichenhpy.cryptoapi.AbstractDecryptHandler;
import cn.beichenhpy.cryptoapi.CryptoApiHelper;
import cn.beichenhpy.cryptoapi.CryptoType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <pre>
 *    对@RequestBody修饰的并且在配置文件配置了对应url以及aesKey，进行参数解密
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 21:23
 */
@Slf4j
@ControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {


    @Resource
    private CryptoApiHelper cryptoApiHelper;

    @Resource
    private HttpServletRequest request;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return cryptoApiHelper.getHandlerKeyOrNull(request.getServletPath(), CryptoType.DECRYPT) != null;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        AbstractDecryptHandler decryptHandler = cryptoApiHelper.getDecryptHandler(request.getServletPath());
        inputMessage = decryptHandler.decryptRequestBody(request, inputMessage, parameter, targetType, converterType);
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }
}
