package cn.beichenhpy.cryptoapi.util;

import cn.beichenhpy.cryptoapi.exception.CryptoApiException;
import cn.beichenhpy.cryptoapi.config.CryptoApiProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
@Component
public class CryptoApiHelper {

    @Resource
    private CryptoApiProperties cryptoApiProperties;

    /**
     * 普通path对应ak的缓存
     */
    private static final Map<String, String> COMMON_PATH_CACHE = new LinkedHashMap<>();

    /**
     * 通配path对应ak的缓存
     */
    private static final Map<String, String> WILDCARD_PATH_CACHE = new LinkedHashMap<>();

    /**
     * 通配符
     */
    private static final String WILDCARD = "*";

    /**
     * 路径匹配
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @PostConstruct
    public void initCache() {
        Map<String, CryptoApiProperties.CryptoPath> cryptoApis = cryptoApiProperties.getApis();
        if (cryptoApis.isEmpty()) {
            return;
        }
        WILDCARD_PATH_CACHE.clear();
        COMMON_PATH_CACHE.clear();
        for (Map.Entry<String, CryptoApiProperties.CryptoPath> entry : cryptoApis.entrySet()) {
            CryptoApiProperties.CryptoPath cryptoPath = entry.getValue();
            List<String> paths = cryptoPath.getPaths();
            String aesKey = cryptoPath.getAesKey();
            List<String> wildcardPathSet = paths.stream()
                    .filter(this::isWildcard)
                    .collect(Collectors.toList());
            for (String wildcardPath : wildcardPathSet) {
                WILDCARD_PATH_CACHE.put(wildcardPath, aesKey);
            }
            //移除通配路径
            paths.removeIf(this::isWildcard);
            if (!paths.isEmpty()) {
                if (!StringUtils.hasText(aesKey)) {
                    throw new CryptoApiException("paths: [" + paths + " ]未填写对应aesKey");
                }
                for (String path : paths) {
                    COMMON_PATH_CACHE.put(path, aesKey);
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
     * 根据访问路径找到对应的aesKey
     *
     * @param path 访问路径
     * @return 返回aesKey
     */
    public String getAesKeyByPath(String path) {
        //先判断是否满足普通的链接
        String ak = COMMON_PATH_CACHE.get(path);
        if (StringUtils.hasText(ak)) {
            return ak;
        }
        //不存在，再匹配通配
        for (Map.Entry<String, String> entry : WILDCARD_PATH_CACHE.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), path)) {
                ak = entry.getValue();
                break;
            }
        }
        return ak;
    }
}
