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

package cn.beichenhpy.encryptdecryptapisample.web;

import cn.beichenhpy.encryptdecryptapisample.annotation.Secret;
import cn.beichenhpy.encryptdecryptapisample.modal.Result;
import cn.beichenhpy.encryptdecryptapisample.util.AES;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * <pre>
 *   针对@ResponseBody并且注解@Secret的方法进行加密，需要根据返回值类型定制
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 21:13
 */
@Slf4j
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Result<String>> {

    @Resource
    private ObjectMapper objectMapper;

    @Value("${encrypt-decrypt-api.aes-key}")
    private String aesKey;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Secret.class);
    }

    @Override
    public Result<String> beforeBodyWrite(Result<String> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            if (body.getData() != null) {
                try {
                    String _data = AES.encrypt(objectMapper.writeValueAsString(body.getData()), aesKey);
                    body.setData(_data);
                } catch (Exception e) {
                    log.error("解密失败");
                }
            }
            if (body.getMsg() != null) {
                try {
                    String _msg = AES.encrypt(objectMapper.writeValueAsString(body.getMsg()), aesKey);
                    body.setMsg(_msg);
                } catch (Exception e) {
                    log.error("解密失败");
                }
            }
        }
        return body;
    }


}
