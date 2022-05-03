/*
 * Copyright [2022] [韩鹏宇]
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

import cn.beichenhpy.encryptdecryptapisample.util.AES;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *   解密@RequestParam的wrapper，配合filter使用
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 19:35
 */
@Slf4j
public class DecryptRequestParamsWrapper extends HttpServletRequestWrapper {

    private final String aesKey;

    private final Map<String, String[]> decryptParameters = new HashMap<>();

    public DecryptRequestParamsWrapper(HttpServletRequest request, String aesKey) {
        super(request);
        this.aesKey = aesKey;
        this.decryptParameters.putAll(request.getParameterMap());
        for (Map.Entry<String, String[]> entry : decryptParameters.entrySet()) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                try {
                    values[i] = decrypt(values[i]);
                } catch (Exception e) {
                    //do nothing
                    log.error("解密失败:{}", e.getMessage());
                }
            }
            decryptParameters.put(entry.getKey(), values);
        }
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(decryptParameters.keySet());
    }

    @Override
    public String getParameter(String name) {
        String[] value = decryptParameters.get(name);
        if (value == null) {
            return null;
        }
        return value[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return decryptParameters;
    }

    @Override
    public String[] getParameterValues(String name) {
        return decryptParameters.get(name);
    }

    @SneakyThrows
    private String decrypt(String param) {
        if (aesKey == null) {
            throw new Exception("aes Key 不存在");
        }
        return AES.decrypt(param, aesKey);
    }
}
