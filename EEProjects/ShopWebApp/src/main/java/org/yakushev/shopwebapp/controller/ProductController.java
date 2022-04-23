package org.yakushev.shopwebapp.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.model.Product;
import org.yakushev.shopwebapp.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private Gson gson;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public String getAll() {
        return gson.toJson(productService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getItemById(@PathVariable Long id) {
        return gson.toJson(productService.getById(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @Transactional
    public String add(@RequestBody Product product) {
        return gson.toJson(productService.add(product));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json")
    @Transactional
    public String update(@RequestBody Product product) {
        return gson.toJson(productService.update(product));
    }


}
