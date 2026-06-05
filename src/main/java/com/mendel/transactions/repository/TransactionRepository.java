package com.mendel.transactions.repository;

import com.mendel.transactions.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    void save(Long id, Transaction transaction);

    Optional<Transaction> findById(Long id);

    List<Transaction> findByType(String type);

    List<Transaction> findByParentId(Long parentId);

    boolean existsById(Long id);
}