package com.mendel.transactions.service;

import com.mendel.transactions.exception.ParentTransactionNotFoundException;
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
            throw new ParentTransactionNotFoundException(transaction.getParentId());
        }
        repository.save(id, transaction);
    }

    @Override
    public List<Long> getIdsByType(String type) {
        return repository.findByType(type).stream()
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateSum(Long id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new ParentTransactionNotFoundException(id));

        double sum = transaction.getAmount();

        for (Transaction child : repository.findByParentId(id)) {
            sum += calculateSum(child.getId());
        }

        return sum;
    }
}
