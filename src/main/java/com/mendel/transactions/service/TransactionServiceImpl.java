package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Long id, Transaction transaction) {
        if (transaction.getParentId() != null && !repository.existsById(transaction.getParentId())) {
            throw new IllegalArgumentException("parent transaction not found");
        }
        repository.save(id, transaction);
    }

    @Override
    public List<Long> getIdsByType(String type) {
        return repository.findByType(type).stream()
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }
}
