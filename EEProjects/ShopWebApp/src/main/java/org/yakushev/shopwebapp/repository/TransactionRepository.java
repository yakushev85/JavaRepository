package org.yakushev.shopwebapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yakushev.shopwebapp.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByIdDesc(Long userId);
}
