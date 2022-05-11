package org.yakushev.shopwebapp.security;

import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtCsrfFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    private final JwtTokenRepository jwtTokenRepository;

    public JwtCsrfFilter(JwtTokenRepository jwtTokenRepository, HandlerExceptionResolver resolver) {
        this.jwtTokenRepository = jwtTokenRepository;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        this.logger.info(request.getMethod() + " " + request.getRequestURL());

        if (SecurityHelper.isPublicUrl(request.getServletPath())) {

            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                resolver.resolveException(request, response, null, new MissingCsrfTokenException(""));
            }
        } else {
            CsrfToken token = jwtTokenRepository.loadToken(request);

            if (token == null) {
                resolver.resolveException(request, response, null, new MissingCsrfTokenException(""));
                return;
            }

            String rawToken = token.getToken();

            try {
                if (StringUtils.isNotEmpty(rawToken)) {
                    String username = jwtTokenRepository.getUsernameFromToken(rawToken);

                    if (StringUtils.isEmpty(username)) {
                        resolver.resolveException(request, response, null, new InvalidCsrfTokenException(token, rawToken));
                        return;
                    }

                    Date expirationDate = jwtTokenRepository.getExpirationDateFromToken(rawToken);

                    long diff = expirationDate.getTime() - (new Date()).getTime();

                    if (diff > 0) {
                        request.setAttribute(CsrfToken.class.getName(), token);
                        request.setAttribute(token.getParameterName(), token);

                        filterChain.doFilter(request, response);
                    } else {
                        resolver.resolveException(request, response, null, new InvalidCsrfTokenException(token, rawToken));
                    }
                } else {
                    resolver.resolveException(request, response, null, new MissingCsrfTokenException(""));
                }
            } catch (JwtException e) {
                resolver.resolveException(request, response, null, new MissingCsrfTokenException(rawToken));
            }
        }
    }
}
