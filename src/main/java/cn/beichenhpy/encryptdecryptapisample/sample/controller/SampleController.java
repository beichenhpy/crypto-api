package cn.beichenhpy.encryptdecryptapisample.sample.controller;

import cn.beichenhpy.encryptdecryptapisample.sample.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
