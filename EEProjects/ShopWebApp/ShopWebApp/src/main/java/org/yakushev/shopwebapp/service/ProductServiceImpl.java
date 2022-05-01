package org.yakushev.shopwebapp.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yakushev.shopwebapp.model.Product;
import org.yakushev.shopwebapp.repository.ProductRepository;
import org.yakushev.shopwebapp.security.JwtTokenRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Override
	public List<Product> getAll() {
		return Lists.newArrayList(productRepository.findAll());
	}

	@Override
	public Product getById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Product add(Product value) {
		value.setId(null);
		value.setCreatedAt(new Date());
		return productRepository.save(value);
	}

	@Override
	@Transactional
	public Product update(Product value) {
		Product oldValue = getById(value.getId());
		value.setCreatedAt(null);
		value.setCreatedBy(null);
		try {
			Product updatedValue = (new Product()).merge(getById(value.getId()), value);
			return productRepository.save(updatedValue);
		} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			return oldValue;
		}
	}

}
