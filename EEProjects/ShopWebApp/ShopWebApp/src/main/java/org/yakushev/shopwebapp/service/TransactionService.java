package org.yakushev.shopwebapp.service;

import org.yakushev.shopwebapp.dto.TransactionRequest;
import org.yakushev.shopwebapp.model.Transaction;

import java.util.List;

public interface TransactionService {

	List<Transaction> getAll();

	Transaction getById(Long id);

	List<Transaction> getByUserId(Long userId);

	Transaction add(TransactionRequest value);

	Transaction update(Transaction value);

}
