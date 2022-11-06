package cn.beichenhpy.cryptoapi;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * abstract class for response encrypt
 *
 * @author beichenhpy
 * <p> 2022/11/6 15:01
 */
public abstract class AbstractEncryptHandler implements CryptoHandler {

    protected static final Map<String, AbstractEncryptHandler> ENCRYPT_KEY_AND_HANDLERS = new LinkedHashMap<>();

    public AbstractEncryptHandler() {
        ENCRYPT_KEY_AND_HANDLERS.put(getCryptoHandlerKey(), this);
    }

    public abstract <T> T encrypt(T body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse response);
}
