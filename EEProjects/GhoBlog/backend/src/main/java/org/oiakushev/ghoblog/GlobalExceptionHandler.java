package org.oiakushev.ghoblog;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NoSuchElementException.class})
    public ErrorInfo handleAuthenticationException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new ErrorInfo(404, "Not found.");
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ErrorInfo handleValidationException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new ErrorInfo(400, "Not valid parameter in request.");
    }

    public record ErrorInfo(Integer errorCode, String errorMessage) {

        public Integer getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
