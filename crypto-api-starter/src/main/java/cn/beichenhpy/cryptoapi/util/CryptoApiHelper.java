package cn.beichenhpy.cryptoapi.util;

import cn.beichenhpy.cryptoapi.CryptoType;
import cn.beichenhpy.cryptoapi.config.CryptoApiProperties;
import cn.beichenhpy.cryptoapi.exception.CryptoApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 加密解密api工具类，用于获取servletPath对应的aesKey
 *
 * @author beichenhpy
 * <p> 2022/7/7 20:31
 */
@Slf4j
@RequiredArgsConstructor
public class CryptoApiHelper {

    /**
     * 普通path对应ak的缓存 encrypt时
     */
    private static final Map<String, String> COMMON_ENCRYPT_PATH_CACHE = new LinkedHashMap<>();
    /**
     * 普通path对应ak的缓存 decrypt时
     */
    private static final Map<String, String> COMMON_DECRYPT_PATH_CACHE = new LinkedHashMap<>();

    /**
     * 通配path对应ak的缓存 encrypt
     */
    private static final Map<String, String> WILDCARD_ENCRYPT_PATH_CACHE = new LinkedHashMap<>();

    /**
     * 通配path对应ak的缓存 decrypt
     */
    private static final Map<String, String> WILDCARD_DECRYPT_PATH_CACHE = new LinkedHashMap<>();

    /**
     * 通配符
     */
    private static final String WILDCARD = "*";

    /**
     * 路径匹配
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * config inject
     */
    private final CryptoApiProperties cryptoApiProperties;

    @PostConstruct
    public synchronized void initCache() {
        //encrypt
        CryptoApiProperties.EncryptApi encrypt = cryptoApiProperties.getEncrypt();
        if (encrypt != null && encrypt.isEnable()) {
            //load encrypt apis
            doInitPath(encrypt.getApis(), COMMON_ENCRYPT_PATH_CACHE, WILDCARD_ENCRYPT_PATH_CACHE);
            log.info("crypto-api-init-encrypt-finish : common: {}, wildcard: {}", COMMON_ENCRYPT_PATH_CACHE, WILDCARD_ENCRYPT_PATH_CACHE);
        }
        //decrypt
        CryptoApiProperties.DecryptApi decrypt = cryptoApiProperties.getDecrypt();
        if (decrypt != null && decrypt.isEnable()) {
            //load encrypt apis
            doInitPath(decrypt.getApis(), COMMON_DECRYPT_PATH_CACHE, WILDCARD_DECRYPT_PATH_CACHE);
            log.info("crypto-api-init-decrypt-finish : common: {}, wildcard: {}", COMMON_DECRYPT_PATH_CACHE, WILDCARD_DECRYPT_PATH_CACHE);
        }
    }


    private void doInitPath(Map<String, CryptoApiProperties.CryptoPath> cryptoApis, Map<String, String> commonPathCache, Map<String, String> wildcardPathCache) {
        if (cryptoApis.isEmpty()) {
            return;
        }
        commonPathCache.clear();
        wildcardPathCache.clear();
        for (Map.Entry<String, CryptoApiProperties.CryptoPath> entry : cryptoApis.entrySet()) {
            CryptoApiProperties.CryptoPath cryptoPath = entry.getValue();
            List<String> paths = cryptoPath.getPaths();
            String aesKey = cryptoPath.getAesKey();
            List<String> wildcardPathSet = paths.stream()
                    .filter(this::isWildcard)
                    .collect(Collectors.toList());
            for (String wildcardPath : wildcardPathSet) {
                wildcardPathCache.put(wildcardPath, aesKey);
            }
            //移除通配路径
            paths.removeIf(this::isWildcard);
            if (!paths.isEmpty()) {
                if (!StringUtils.hasText(aesKey)) {
                    throw new CryptoApiException("paths: [" + paths + " ]未填写对应aesKey");
                }
                for (String path : paths) {
                    commonPathCache.put(path, aesKey);
                }
            }
        }
    }

    /**
     * 判断是否为通配
     *
     * @param path servletPath
     * @return 是返回true 否则返回false
     */
    private boolean isWildcard(String path) {
        return path.contains(WILDCARD);
    }


    /**
     * 区分加密还是解密并且根据访问路径找到对应的aesKey
     *
     * @param path       访问路径
     * @param cryptoType 加密/解密类型
     * @return 返回aesKey
     */
    public String getAesKeyOrNull(String path, CryptoType cryptoType) {
        switch (cryptoType) {
            case DECRYPT:
                return doGetAesKeyOrNull(path, COMMON_DECRYPT_PATH_CACHE, WILDCARD_DECRYPT_PATH_CACHE);
            case ENCRYPT:
                return doGetAesKeyOrNull(path, COMMON_ENCRYPT_PATH_CACHE, WILDCARD_ENCRYPT_PATH_CACHE);
        }
        return null;
    }

    /**
     * 根据访问路径找到对应的aesKey
     *
     * @param path              请求路径
     * @param commonPathCache   普通的路径缓存
     * @param wildcardPathCache 通配路径缓存
     * @return 返回对应的aesKey，没有返回null
     */
    private String doGetAesKeyOrNull(String path, Map<String, String> commonPathCache, Map<String, String> wildcardPathCache) {
        //先判断是否满足普通的链接
        String encryptAesKey = commonPathCache.get(path);
        if (StringUtils.hasText(encryptAesKey)) {
            return encryptAesKey;
        }
        //不存在，再匹配通配
        for (Map.Entry<String, String> entry : wildcardPathCache.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), path)) {
                encryptAesKey = entry.getValue();
                break;
            }
        }
        return encryptAesKey;
    }
}
