package cn.beichenhpy.encryptdecryptapisample.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *  用于@RequestBody解密和@ResponseBody的加密生效
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 20:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secret {
}
