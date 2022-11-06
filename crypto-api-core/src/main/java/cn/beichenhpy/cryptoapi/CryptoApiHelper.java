package cn.beichenhpy.cryptoapi;

import cn.beichenhpy.cryptoapi.exception.CryptoApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 加密解密api工具类，用于获取servletPath对应的handlerKey
 *
 * @author beichenhpy
 * <p> 2022/7/7 20:31
 */
@Slf4j
@RequiredArgsConstructor
public class CryptoApiHelper {


    public AbstractDecryptHandler getDecryptHandler(String handler) {
        AbstractDecryptHandler decryptHandler = AbstractDecryptHandler.DECRYPT_KEY_AND_HANDLERS.get(handler);
        if (decryptHandler == null) {
            throw new CryptoApiException("this handler [" + handler + "] has not implement for decrypt");
        }
        return decryptHandler;
    }

    public AbstractEncryptHandler getEncryptHandler(String handler) {
        AbstractEncryptHandler encryptHandler = AbstractEncryptHandler.ENCRYPT_KEY_AND_HANDLERS.get(handler);
        if (encryptHandler == null) {
            throw new CryptoApiException("this handler [" + handler + "] has not implement for encrypt");
        }
        return encryptHandler;
    }
}
