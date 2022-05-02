package cn.beichenhpy.encryptdecryptapisample.sample.controller;

import cn.beichenhpy.encryptdecryptapisample.annotation.Secret;
import cn.beichenhpy.encryptdecryptapisample.modal.Result;
import cn.beichenhpy.encryptdecryptapisample.sample.entity.User;
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


    @Secret
    @GetMapping("/query")
    public Result<User> query(@RequestBody User user) {
        User result = new User();
        result.setBirthday(LocalDateTime.of(1997, 6, 24, 0, 0, 0));
        result.setAge(24);
        result.setMoney(new BigDecimal("65000.22"));
        result.setUsername("韩鹏宇");
        log.info("user: {}", user);
        return Result.ok(result);
    }
}
