package org.yakushev.shopwebapp.service;

import org.yakushev.shopwebapp.model.Transaction;

import java.util.List;

public interface TransactionService {

	List<Transaction> getAll();

	Transaction getById(Long id);

	Transaction add(Transaction value);

	Transaction update(Transaction value);

}
