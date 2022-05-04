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

package cn.beichenhpy.encryptdecryptapisample.filter;

import cn.beichenhpy.encryptdecryptapisample.web.DecryptRequestParamsWrapper;
import cn.beichenhpy.encryptdecryptapisample.web.config.EncryptDecryptApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *   解密@RequestParam符合AES加密的参数的过滤器
 *   根据encrypt-decrypt-api:urls 设置的需要解密的url进行过滤
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 19:54
 */
@Slf4j
@Component
public class DecryptParameterFilter extends OncePerRequestFilter {

    @Resource
    private EncryptDecryptApiProperties encryptDecryptApiProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !encryptDecryptApiProperties.getUrls().contains(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request = new DecryptRequestParamsWrapper(request, encryptDecryptApiProperties.getAesKey());
        filterChain.doFilter(request, response);
    }
}
