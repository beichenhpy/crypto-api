package cn.beichenhpy.cryptoapi.exception;

/**
 * @author beichenhpy
 * <p> 2022/7/7 21:06
 */
public class CryptoApiException extends RuntimeException{

    public CryptoApiException(String message) {
        super(message);
    }

    public CryptoApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
