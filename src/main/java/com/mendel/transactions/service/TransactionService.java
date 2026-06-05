package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;

public interface TransactionService {
    void save(Long id, Transaction transaction);
}