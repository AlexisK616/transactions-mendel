package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Long id, Transaction transaction) {
        repository.save(id, transaction);
    }
}
