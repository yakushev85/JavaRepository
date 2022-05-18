package org.yakushev.shopwebapp.security;

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
            filterChain.doFilter(request, response);
            return;
        }

        CsrfToken token = jwtTokenRepository.loadToken(request);

        if (token == null || StringUtils.isEmpty(token.getToken())) {
            resolver.resolveException(request, response, null, new MissingCsrfTokenException(""));
            return;
        }

        String rawToken = token.getToken();
        String username = jwtTokenRepository.getUsernameFromToken(rawToken);

        if (StringUtils.isEmpty(username)) {
            resolver.resolveException(request, response, null, new InvalidCsrfTokenException(token, rawToken));
            return;
        }

        Date expirationDate = jwtTokenRepository.getExpirationDateFromToken(rawToken);
        long diff = expirationDate.getTime() - (new Date()).getTime();

        if (diff > 0) {
            request.setAttribute(token.getParameterName(), token);

            filterChain.doFilter(request, response);
        } else {
            resolver.resolveException(request, response, null, new InvalidCsrfTokenException(token, rawToken));
        }
    }
}
