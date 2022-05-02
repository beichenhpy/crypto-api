package cn.beichenhpy.encryptdecryptapisample.web;

import cn.beichenhpy.encryptdecryptapisample.annotation.Secret;
import cn.beichenhpy.encryptdecryptapisample.util.AES;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 21:13
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<ResponseEntity<String>> {

    @Resource
    private ObjectMapper objectMapper;

    @Value("${encrypt-decrypt-api.aes-key}")
    private String aesKey;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Secret.class);
    }


    @SneakyThrows
    @Override
    public ResponseEntity<String> beforeBodyWrite(ResponseEntity<String> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            if (body.getBody() != null) {
                String _body = AES.encrypt(objectMapper.writeValueAsString(body.getBody()), aesKey);
                return ResponseEntity.status(body.getStatusCode())
                        .headers(body.getHeaders())
                        .body(_body);
            }
        }
        return body;
    }
}
