package com.mendel.transactions.repository;


import com.mendel.transactions.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final Map<Long, Transaction> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Long id, Transaction transaction) {
        storage.put(id, transaction);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Transaction> findByType(String type) {
        return storage.values().stream()
                .filter(t -> t.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByParentId(Long parentId) {
        return storage.values().stream()
                .filter(t -> parentId.equals(t.getParentId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}