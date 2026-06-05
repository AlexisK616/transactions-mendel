package com.mendel.transactions.service;

import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionServiceImpl  service;


    @Test
    void shouldSaveTransactionWithoutParent() {
        Transaction transaction = Transaction.builder()
                .id(10L)
                .amount(5000.0)
                .type("cars")
                .build();

        service.save(10L, transaction);
    }

    @Test
    void shouldThrowExceptionWhenParentDoesNotExist() {
        Transaction transaction = Transaction.builder()
                .id(11L)
                .amount(10000.0)
                .type("shopping")
                .parentId(99L)
                .build();

        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.save(11L, transaction))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("parent");
    }


    @Test
    void shouldReturnTransactionIdsByType() {
        Transaction t = Transaction.builder()
                .id(10L)
                .amount(5000.0)
                .type("cars")
                .build();

        when(repository.findByType("cars")).thenReturn(List.of(t));

        List<Long> ids = service.getIdsByType("cars");

        assertThat(ids).containsExactly(10L);
    }

    @Test
    void shouldCalculateTransitiveSum() {
        Transaction t10 = Transaction.builder()
                .id(10L).amount(5000.0).type("cars").build();
        Transaction t11 = Transaction.builder()
                .id(11L).amount(10000.0).type("shopping").parentId(10L).build();
        Transaction t12 = Transaction.builder()
                .id(12L).amount(5000.0).type("shopping").parentId(11L).build();

        when(repository.findById(10L)).thenReturn(Optional.of(t10));
        when(repository.findById(11L)).thenReturn(Optional.of(t11));
        when(repository.findById(12L)).thenReturn(Optional.of(t12));
        when(repository.findByParentId(10L)).thenReturn(List.of(t11));
        when(repository.findByParentId(11L)).thenReturn(List.of(t12));
        when(repository.findByParentId(12L)).thenReturn(List.of());

        Double sum = service.calculateSum(10L);

        assertThat(sum).isEqualTo(20000.0);
    }
}
