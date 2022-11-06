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

package com.sample.foo.controller;

import cn.beichenhpy.cryptoapi.DecryptApi;
import cn.beichenhpy.cryptoapi.EncryptApi;
import com.sample.foo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 20:02
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class SampleController {

    /**
     * <pre>
     *      OkHttpClient client = new OkHttpClient().newBuilder().build();
     *      MediaType mediaType = MediaType.parse("text/plain");
     *      RequestBody body = RequestBody.create(mediaType, "");
     *      Request request = new Request.Builder()
     *        .url("http://localhost:8080/demo/encrypt/user")
     *        .method("GET", body)
     *        .build();
     *      Response response = client.newCall(request).execute();
     * </pre>
     * @return 返回加密后的用户信息
     */
    @EncryptApi(handler = "AES")
    @GetMapping("/encrypt/user")
    public List<User> encryptUser() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("韩鹏宇 | " + i, LocalDateTime.of(LocalDate.of(1997, 6, 24), LocalTime.of(6, 10, 23)), 25, BigDecimal.valueOf(70000L)));
        }
        return users;
    }

    /**
     * <pre>
     *      OkHttpClient client = new OkHttpClient().newBuilder().build();
     *      MediaType mediaType = MediaType.parse("application/json");
     *      RequestBody body = RequestBody.create(mediaType, "iMB9/BQ6EvzT8hlZHlAlIlorXjb4/0NRnW6WWu70Z8iqrPAOVxR3FxxxxjE93koNKUcwgU2VCJ6dW+HWmpxw9n9FZhtT4H6bctuF3ew7FlmQGiH5TYGQN91rDeD0wN3P");
     *      Request request = new Request.Builder()
     *          .url("http://localhost:8080/demo/decrypt/user")
     *          .method("GET", body)
     *          .addHeader("Content-Type", "application/json")
     *          .build();
     *      Response response = client.newCall(request).execute();
     * </pre>
     * @param decryptUser 解密后的用户信息
     */
    @DecryptApi(handler = "AES")
    @GetMapping("/decrypt/user")
    public void decryptUser(@RequestBody List<User> decryptUser) {
        log.info("decrypt user : {}", decryptUser);
    }

}
