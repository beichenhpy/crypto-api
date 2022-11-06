package cn.beichenhpy.cryptoapi.extension.aes;

import cn.beichenhpy.cryptoapi.AbstractDecryptHandler;
import cn.beichenhpy.cryptoapi.CryptoType;
import cn.beichenhpy.cryptoapi.extension.util.AES;
import cn.beichenhpy.cryptoapi.extension.util.AesCryptoHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author beichenhpy
 * <p> 2022/11/6 16:39
 */
@Slf4j
@RequiredArgsConstructor
public class AesDecryptRequestHandler extends AbstractDecryptHandler {

    private final AesCryptoHelper aesCryptoHelper;

    @Override
    public HttpInputMessage decryptRequestBody(HttpServletRequest request, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        InputStream body = inputMessage.getBody();
        String aesKey = aesCryptoHelper.getAesKey(request.getServletPath(), CryptoType.DECRYPT);
        //copy stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = body.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        try {
            String decrypt = AES.decrypt(byteArrayOutputStream.toString(), aesKey);
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return new ByteArrayInputStream(decrypt.getBytes(StandardCharsets.UTF_8));
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            log.error("AES KEY: {}, 参数解密失败: {}, {}", aesKey, e.getMessage(), e);
        } finally {
            byteArrayOutputStream.close();
        }
        return inputMessage;
    }

    @Override
    public String getCryptoHandlerKey() {
        return "AES";
    }
}
