package org.yakushev.shopwebapp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;
import org.yakushev.shopwebapp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

@Repository
public class JwtTokenRepository implements CsrfTokenRepository {
    private static final String HEADER_NAME = "x-csrf-token";
    private static final String PARAMETER_NAME = "_csrf";

    private final String secret;

    public JwtTokenRepository() {
        this.secret = "rmG-nAbtNNof_ALwo2HH";
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant());

        String username = (String) httpServletRequest.getAttribute(User.class.getName());

        String token = "";

        try {
            token = Jwts.builder()
                    .setId(id)
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
        }

        return new DefaultCsrfToken(HEADER_NAME, PARAMETER_NAME, token);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.nonNull(csrfToken)) {
            if (!response.getHeaderNames().contains(ACCESS_CONTROL_EXPOSE_HEADERS)) {
                response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, csrfToken.getHeaderName());
            }

            if (response.getHeaderNames().contains(csrfToken.getHeaderName())) {
                response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            } else {
                response.addHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            }

            request.getSession().setAttribute(PARAMETER_NAME, csrfToken.getToken());
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        Object tokenFromSession = request.getSession().getAttribute(PARAMETER_NAME);
        String tokenFromHeader = request.getHeader(HEADER_NAME);

        if (tokenFromSession != null) {
            return new DefaultCsrfToken(HEADER_NAME, PARAMETER_NAME, (String) tokenFromSession);
        } else if (tokenFromHeader != null) {
            return new DefaultCsrfToken(HEADER_NAME, PARAMETER_NAME, tokenFromHeader);
        } else {
            return null;
        }
    }

    public void clearToken(HttpServletResponse response) {
        if (response.getHeaderNames().contains(HEADER_NAME))
            response.setHeader(HEADER_NAME, "");
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
