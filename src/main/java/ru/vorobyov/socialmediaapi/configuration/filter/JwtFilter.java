package ru.vorobyov.socialmediaapi.configuration.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.vorobyov.socialmediaapi.model.JwtAuthentication;
import ru.vorobyov.socialmediaapi.service.jwt.JwtProvider;

import java.io.IOException;
import java.util.Map;

/**
 * The type Jwt filter.
 */
@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * Filter method.
     *
     * @param request the request
     * @param response the response
     * @param fc the filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null && jwtProvider.validateAccessToken(token)) {
            final Claims claims = jwtProvider.getAccessClaims(token);
            final JwtAuthentication jwtInfoToken = generateInfoToken(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            final int tokenIndex = 7;
            return bearer.substring(tokenIndex);
        }
        return null;
    }

    public static JwtAuthentication generateInfoToken(Map<String, Object> claims) {

        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setEmail((String) claims.get("email"));

        return jwtAuthentication;
    }

}