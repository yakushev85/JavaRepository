package org.yakushev.shopwebapp.security;

import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
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

        String servletPath = request.getServletPath();

        if (SecurityHelper.isPublicUrl(servletPath)) {

            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                resolver.resolveException(request, response, null, new MissingCsrfTokenException(""));
            }
        } else {
            CsrfToken attributeToken = jwtTokenRepository.loadToken(request);
            String actualToken = (attributeToken == null)? request.getHeader("x-csrf-token") : attributeToken.getToken();

            try {
                if (StringUtils.isNotEmpty(actualToken)) {
                    CsrfToken csrfToken = new DefaultCsrfToken("x-csrf-token", "_csrf", actualToken);
                    String username = jwtTokenRepository.getUsernameFromToken(actualToken);

                    if (StringUtils.isEmpty(username)) {
                        resolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
                        return;
                    }

                    Date expirationDate = jwtTokenRepository.getExpirationDateFromToken(actualToken);

                    long diff = expirationDate.getTime() - (new Date()).getTime();

                    if (diff > 0) {
                        request.setAttribute(CsrfToken.class.getName(), csrfToken);
                        request.setAttribute(csrfToken.getParameterName(), csrfToken);

                        filterChain.doFilter(request, response);
                    } else {
                        resolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
                    }
                } else {
                    resolver.resolveException(request, response, null, new MissingCsrfTokenException(""));
                }
            } catch (JwtException e) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Invalid CSRF token found for " + UrlUtils.buildFullRequestUrl(request));
                }

                resolver.resolveException(request, response, null, new MissingCsrfTokenException(actualToken));
            }
        }
    }
}
