package cn.beichenhpy.encryptdecryptapisample.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 22:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private Boolean success;
    private T data;
    private String msg;


    public static <T> Result<T> ok(T data, String msg) {
        return new Result<>(200, true, data, msg);
    }


    public static <T> Result<T> ok(T data) {
        return new Result<>(200, true, data, "请求成功");
    }
}
