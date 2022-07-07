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

import cn.beichenhpy.cryptoapi.util.AES;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.foo.entity.User;
import com.sample.foo.modal.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("user")
    public void test1(User user) {
        log.info("user: {}", user);
    }


    @PostMapping("/query")
    public Result<User> query(@RequestBody(required = false) User user) throws Exception {
        User result = new User();
        result.setBirthday(LocalDateTime.of(1997, 6, 24, 0, 0, 0));
        result.setAge(24);
        result.setMoney(new BigDecimal("65000.22"));
        result.setUsername("韩鹏宇");
        log.info("user: {}", user);
        log.info("encrypt user: {}", AES.encrypt(objectMapper.writeValueAsString(result), "f5d830d77163a58f"));
        return Result.ok(result);
    }
}
