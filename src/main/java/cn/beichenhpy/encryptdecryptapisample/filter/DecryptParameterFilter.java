package cn.beichenhpy.encryptdecryptapisample.filter;

import cn.beichenhpy.encryptdecryptapisample.web.DecryptRequestParamsWrapper;
import cn.beichenhpy.encryptdecryptapisample.web.config.EncryptDecryptApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author beichenhpy
 * <p> 2022/5/2 19:54
 */
@Slf4j
@Component
public class DecryptParameterFilter extends OncePerRequestFilter {

    @Resource
    private EncryptDecryptApiProperties encryptDecryptApiProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> decryptUrls = encryptDecryptApiProperties.getDecryptUrls();
        if (decryptUrls.isEmpty()) {
            return true;
        } else {
            return !decryptUrls.contains(request.getServletPath());
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request = new DecryptRequestParamsWrapper(request, encryptDecryptApiProperties.getAesKey());
        filterChain.doFilter(request, response);
    }
}
