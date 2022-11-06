package cn.beichenhpy.cryptoapi.extension.aes;

import cn.beichenhpy.cryptoapi.AbstractEncryptHandler;
import cn.beichenhpy.cryptoapi.CryptoType;
import cn.beichenhpy.cryptoapi.extension.util.AES;
import cn.beichenhpy.cryptoapi.extension.util.AesCryptoHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author beichenhpy
 * <p> 2022/11/6 16:50
 */
@Slf4j
@RequiredArgsConstructor
public class AesEncryptResponseHandler extends AbstractEncryptHandler {

    private final ObjectMapper objectMapper;

    private final AesCryptoHelper aesCryptoHelper;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T encrypt(T body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse response) {
        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest servletRequest = request.getServletRequest();
        String aesKey = aesCryptoHelper.getAesKey(servletRequest.getServletPath(), CryptoType.ENCRYPT);
        try {
            return (T) AES.encrypt(objectMapper.writeValueAsString(body), aesKey);
        } catch (Exception e) {
            log.error("AES KEY: {}, 返回值加密失败: {}, {}", aesKey, e.getMessage(), e);
        }
        return body;
    }

    @Override
    public String getCryptoHandlerKey() {
        return "AES";
    }
}
