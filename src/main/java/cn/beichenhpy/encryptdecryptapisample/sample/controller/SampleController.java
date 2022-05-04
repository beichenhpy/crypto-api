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

package cn.beichenhpy.encryptdecryptapisample.sample.controller;

import cn.beichenhpy.encryptdecryptapisample.sample.entity.User;
import cn.beichenhpy.encryptdecryptapisample.sample.modal.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
public class SampleController {

    @GetMapping("user")
    public void test1(User user) {
        log.info("user: {}", user);
    }


    @GetMapping("/query")
    public Result<User> query(@RequestBody(required = false) User user) {
        User result = new User();
        result.setBirthday(LocalDateTime.of(1997, 6, 24, 0, 0, 0));
        result.setAge(24);
        result.setMoney(new BigDecimal("65000.22"));
        result.setUsername("韩鹏宇");
        log.info("user: {}", user);
        return Result.ok(result);
    }
}
