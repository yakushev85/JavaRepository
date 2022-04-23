package org.yakushev.shopwebapp.service;

import org.yakushev.shopwebapp.model.Product;

import java.util.List;

public interface ProductService {

	List<Product> getAll();

	Product getById(Long id);

	Product add(Product value);

	Product update(Product value);

}
