package cn.beichenhpy.cryptoapi;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * abstract class for request decrypt
 *
 * @author beichenhpy
 * <p> 2022/11/6 15:01
 */
public abstract class AbstractDecryptHandler implements CryptoHandler {

    protected static final Map<String, AbstractDecryptHandler> DECRYPT_KEY_AND_HANDLERS = new LinkedHashMap<>();

    public AbstractDecryptHandler() {
        DECRYPT_KEY_AND_HANDLERS.put(getCryptoHandlerKey(), this);
    }

    public abstract HttpInputMessage decryptRequestBody(HttpServletRequest request, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException;

    public abstract Map<String, String[]> decryptRequestParam(HttpServletRequest request);
}
