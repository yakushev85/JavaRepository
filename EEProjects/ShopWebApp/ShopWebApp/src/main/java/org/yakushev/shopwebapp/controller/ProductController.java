package org.yakushev.shopwebapp.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.model.Product;
import org.yakushev.shopwebapp.service.ProductService;
import org.yakushev.shopwebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"http://swa_frontend:4200", "http://localhost:4200", "http://0.0.0.0:4200"})
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private Gson gson;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public String getAll(@RequestParam(name="page", defaultValue = "0") Integer page,
                         @RequestParam(name="size", defaultValue = "10") Integer size) {
        return gson.toJson(productService.getAll(PageRequest.of(page, size)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getItemById(@PathVariable Long id) {
        return gson.toJson(productService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public String add(@RequestBody Product product, HttpServletRequest request) {
        userService.checkAdminRole(request);
        return gson.toJson(productService.add(product));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public String update(@RequestBody Product product, HttpServletRequest request) {
        userService.checkAdminRole(request);
        return gson.toJson(productService.update(product));
    }
}
