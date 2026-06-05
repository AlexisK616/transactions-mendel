package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionServiceImpl service;


    @Test
    void shouldSaveTransactionWithoutParent() {
        Transaction transaction = Transaction.builder()
                .id(10L)
                .amount(5000.0)
                .type("cars")
                .build();

        service.save(10L, transaction);
    }
}
