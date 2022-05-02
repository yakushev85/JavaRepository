package org.yakushev.shopwebapp.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.model.Transaction;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.service.TransactionService;
import org.yakushev.shopwebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin(origins = {"http://swa_frontend:4200", "http://localhost:4200", "http://0.0.0.0:4200"})
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private Gson gson;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public String getAll(HttpServletRequest request) {
        if (isAdminRole(request)) {
            return gson.toJson(transactionService.getAll());
        } else {
            User user = userService.getUserFromRequest(request);

            if (user == null) {
                return "{}";
            }

            return gson.toJson(transactionService.getByUserId(user.getId()));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getItemById(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);

        if (user == null) {
            return "{}";
        }

        Transaction transaction = transactionService.getById(id);
        if ((!isAdminRole(request) && user.getId() != null && transaction.getUserId().equals(user.getId())) ||
                        isAdminRole(request)) {

            return gson.toJson(transaction);
        } else {
            return "{}";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @Transactional
    public String add(@RequestBody Transaction transaction, HttpServletRequest request) {
        if (isAdminRole(request)) {
            return gson.toJson(transactionService.add(transaction));
        } else {
            User user = userService.getUserFromRequest(request);

            if (user == null) {
                return "{}";
            }

            transaction.setUserId(user.getId());

            return gson.toJson(transactionService.add(transaction));
        }
    }

    private boolean isAdminRole(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);

        return user != null && user.getRole() != null && user.getRole().equalsIgnoreCase("admin");
    }
}
