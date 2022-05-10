package org.yakushev.shopwebapp.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.dto.PasswordRequest;
import org.yakushev.shopwebapp.dto.UserResponse;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"http://swa_frontend:4200", "http://localhost:4200", "http://0.0.0.0:4200"})
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private Gson gson;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public String getAll(HttpServletRequest request) {
        userService.checkAdminRole(request);
        return gson.toJson(UserResponse.fromUsers(userService.getAll()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getItemById(@PathVariable Long id, HttpServletRequest request) {
        userService.checkAdminRole(request);
        return gson.toJson(UserResponse.fromUser(userService.getById(id)));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @Transactional
    public String add(@RequestBody User user, HttpServletRequest request) {
        userService.checkAdminRole(request);
        return gson.toJson(UserResponse.fromUser(userService.add(user)));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json")
    @Transactional
    public String update(@RequestBody User user, HttpServletRequest request) {
        userService.checkAdminRole(request);
        return gson.toJson(UserResponse.fromUser(userService.update(user)));
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST, produces = "application/json")
    @Transactional
    public String setPassword(@RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (user == null || !passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password.");
        }

        return gson.toJson(UserResponse.fromUser(userService.update(user)));
    }
}
