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
import java.util.Set;
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
     * 通配path对应ak的缓存 <br>
     * 通配类型url指 /demo/** /demo1/* 以*号结尾的
     */
    private static final Map<String, String> WILDCARD_PATH_CACHE = new LinkedHashMap<>();

    /**
     * 通配符
     */
    private static final String WILDCARD = "*";

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
                WILDCARD_PATH_CACHE.put(getPathWithoutWildcard(wildcardPath), aesKey);
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
        return path.endsWith(WILDCARD);
    }

    /**
     * 清理通配符的path
     *
     * @param path 原始路径
     * @return 返回去掉末尾的 * 和 /的
     */
    private String getPathWithoutWildcard(String path) {
        //首先去除结尾的 *
        while (path.lastIndexOf(WILDCARD) != -1) {
            path = path.substring(0, path.lastIndexOf(WILDCARD));
        }
        //然后去除 多余的 /
        while (path.lastIndexOf("/") == path.length() - 1) {
            path = path.substring(0, path.lastIndexOf("/"));
        }
        return path;
    }

    /**
     * 根据访问路径找到对应的aesKey
     *
     * @param path 访问路径
     * @return 返回aesKey
     */
    public String getAesKeyByPath(String path) {
        //先判断是否满足通配符
        for (Map.Entry<String, String> entry : WILDCARD_PATH_CACHE.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return COMMON_PATH_CACHE.get(path);
    }
}
