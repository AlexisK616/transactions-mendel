package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;

import java.util.List;

public interface TransactionService {
    void save(Long id, Transaction transaction);
    List<Long> getIdsByType(String type);
}