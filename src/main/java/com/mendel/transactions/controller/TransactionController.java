package com.mendel.transactions.controller;


import com.mendel.transactions.dto.TransactionRequest;
import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> createTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequest request) {

        Transaction transaction = Transaction.builder()
                .id(id)
                .amount(request.getAmount())
                .type(request.getType())
                .parentId(request.getParentId())
                .build();

        transactionService.save(id, transaction);

        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}