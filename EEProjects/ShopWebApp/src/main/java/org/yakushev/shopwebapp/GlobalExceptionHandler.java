package org.yakushev.shopwebapp;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.yakushev.shopwebapp.security.JwtTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private JwtTokenRepository tokenRepository;

    public GlobalExceptionHandler(JwtTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @ExceptionHandler({AuthenticationException.class, SessionAuthenticationException.class})
    public ErrorInfo handleAuthenticationException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        this.tokenRepository.clearToken(response);
        return new ErrorInfo(UrlUtils.buildFullRequestUrl(request), ErrorInfoStatus.AUTHENTICATION_ERROR, 500, "authorization error");
    }

    @ExceptionHandler({MissingCsrfTokenException.class, InvalidCsrfTokenException.class})
    public ErrorInfo handleCsrfException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        this.tokenRepository.clearToken(response);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return new ErrorInfo(UrlUtils.buildFullRequestUrl(request), ErrorInfoStatus.AUTHENTICATION_ERROR, 500, "incorrect or missing csrf token");
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ErrorInfo handleValidationException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ErrorInfo(UrlUtils.buildFullRequestUrl(request), ErrorInfoStatus.VALIDATION_ERROR, 500, ex.getMessage());
    }

    public enum ErrorInfoStatus {
        AUTHENTICATION_ERROR,
        VALIDATION_ERROR
    }

    public class ErrorInfo {
        private final String url;
        private final ErrorInfoStatus status;
        private final Integer statusCode;
        private final String errorMessage;

        ErrorInfo(String url, ErrorInfoStatus status, Integer statusCode, String errorMessage) {
            this.url = url;
            this.status = status;
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
        }

        public String getUrl() {
            return url;
        }

        public ErrorInfoStatus getStatus() {
            return status;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}