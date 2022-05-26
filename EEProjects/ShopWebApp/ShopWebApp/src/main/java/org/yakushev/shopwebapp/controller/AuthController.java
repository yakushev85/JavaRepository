package org.yakushev.shopwebapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.dto.AuthRequest;
import org.yakushev.shopwebapp.dto.AuthResponse;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.security.JwtTokenRepository;
import org.yakushev.shopwebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = {"http://swa_frontend:4200", "http://localhost:4200", "http://0.0.0.0:4200"})
@RestController
@RequestMapping("/")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Transactional
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public AuthResponse loginUser(@RequestBody AuthRequest authRequest,
                                  HttpServletRequest request, HttpServletResponse response) {
        User resolvedUser = userService.findByUsernameOrderByIdDesc(authRequest.getUsername());

        if (resolvedUser != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!passwordEncoder.matches(authRequest.getPassword(), resolvedUser.getPassword())) {
                throw new IllegalArgumentException("Wrong password.");
            }

            if (resolvedUser.getRole() != null && resolvedUser.getRole().equalsIgnoreCase("locked")) {
                throw new IllegalArgumentException("User is locked.");
            }

            request.setAttribute(User.class.getName(), resolvedUser.getUsername());
            String token = jwtTokenRepository.generateToken(request);
            jwtTokenRepository.saveToken(token, request, response);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(resolvedUser.getId());
            authResponse.setUsername(resolvedUser.getUsername());
            authResponse.setToken(token);
            authResponse.setRole(resolvedUser.getRole());

            return authResponse;
        } else {
            throw new IllegalArgumentException("Wrong username.");
        }
    }

    @Transactional
    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public AuthResponse signupUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        User resolvedUser = userService.findByUsernameOrderByIdDesc(user.getUsername());

        if (resolvedUser == null) {
            User storedUser = userService.add(user);

            if (storedUser != null) {
                request.setAttribute(User.class.getName(), storedUser.getUsername());
                String token = jwtTokenRepository.generateToken(request);
                jwtTokenRepository.saveToken(token, request, response);

                AuthResponse authResponse = new AuthResponse();
                authResponse.setId(storedUser.getId());
                authResponse.setUsername(storedUser.getUsername());
                authResponse.setToken(token);
                authResponse.setRole(storedUser.getRole());

                return authResponse;
            }
        }

        throw new IllegalArgumentException("Username is already used.");
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public AuthResponse logout(HttpServletRequest request, HttpServletResponse response) {
        String actualToken = jwtTokenRepository.loadToken(request);

        if (StringUtils.isNotEmpty(actualToken)) {
            jwtTokenRepository.clearToken(response);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(actualToken);

        return authResponse;
    }
}
