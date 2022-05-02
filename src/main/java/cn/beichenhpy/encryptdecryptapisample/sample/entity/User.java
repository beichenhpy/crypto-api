package cn.beichenhpy.encryptdecryptapisample.sample.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <pre>
 *
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 19:58
 */
@ToString
@Data
public class User {

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    private Integer age;

    private BigDecimal money;
}
