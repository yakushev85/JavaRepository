package org.yakushev.shopwebapp.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.dto.TransactionRequest;
import org.yakushev.shopwebapp.model.Transaction;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.service.TransactionService;
import org.yakushev.shopwebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public String getAll(@RequestParam(name="page", defaultValue = "0") Integer page,
                         @RequestParam(name="size", defaultValue = "10") Integer size,
                         HttpServletRequest request) {
        if (userService.isAdminRole(request)) {
            return gson.toJson(transactionService.getAll(PageRequest.of(page, size)));
        } else {
            User user = userService.getUserFromRequest(request);

            if (user == null) {
                throw new MissingCsrfTokenException("");
            }

            return gson.toJson(transactionService.getByUserId(user.getId(), PageRequest.of(page, size)));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getItemById(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);

        if (user == null) {
            throw new MissingCsrfTokenException("");
        }

        Transaction transaction = transactionService.getById(id);
        if ((!userService.isAdminRole(request) && user.getId() != null && transaction.getUser().getId().equals(user.getId())) ||
                userService.isAdminRole(request)) {

            return gson.toJson(transaction);
        } else {
            throw new MissingCsrfTokenException("");
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @Transactional
    public String add(@RequestBody TransactionRequest transactionDto, HttpServletRequest request) {
        if (userService.isAdminRole(request)) {
            return gson.toJson(transactionService.add(transactionDto));
        } else {
            User user = userService.getUserFromRequest(request);

            if (user == null) {
                throw new MissingCsrfTokenException("");
            }

            transactionDto.setUserId(user.getId());

            return gson.toJson(transactionService.add(transactionDto));
        }
    }
}
