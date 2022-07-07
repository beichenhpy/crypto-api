package cn.beichenhpy.cryptoapi.util;

import cn.beichenhpy.cryptoapi.exception.CryptoApiException;
import cn.beichenhpy.cryptoapi.web.config.CryptoApiProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 加密解密api工具类，用于获取servletPath对应的aesKey
 *
 * @author beichenhpy
 * <p> 2022/7/7 20:31
 */
@Component
public class CryptoApiHelper {

    @Resource
    private CryptoApiProperties cryptoApiProperties;

    private static final Map<String, String> CRYPTO_MODEL_CACHE = new LinkedHashMap<>();

    @PostConstruct
    public void initCache() {
        Map<String, CryptoApiProperties.CryptoUrl> cryptoUrls = cryptoApiProperties.getApis();
        if (cryptoUrls.isEmpty()) {
            return;
        }
        CRYPTO_MODEL_CACHE.clear();
        for (Map.Entry<String, CryptoApiProperties.CryptoUrl> entry : cryptoUrls.entrySet()) {
            CryptoApiProperties.CryptoUrl cryptoUrl = entry.getValue();
            List<String> urls = cryptoUrl.getUrls();
            String aesKey = cryptoUrl.getAesKey();
            if (!urls.isEmpty()) {
                if (!StringUtils.hasText(aesKey)) {
                    throw new CryptoApiException("urls: [" + urls + " ]未填写对应aesKey");
                }
                for (String url : urls) {
                    CRYPTO_MODEL_CACHE.put(url, aesKey);
                }
            }
        }
    }

    public String getAesKeyByUrl(String url) {
        return CRYPTO_MODEL_CACHE.get(url);
    }
}
