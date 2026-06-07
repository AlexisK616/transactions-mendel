package com.mendel.transactions.controller;


import com.mendel.transactions.dto.StatusResponse;
import com.mendel.transactions.dto.SumResponse;
import com.mendel.transactions.dto.TransactionRequest;
import com.mendel.transactions.model.Transaction;
import com.mendel.transactions.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "API para gestión de transacciones")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Crear transacción", description = "Crea una nueva transacción. El parent_id es opcional.")
    @PutMapping("/{id}")
    public ResponseEntity<StatusResponse> createTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequest request) {

        Transaction transaction = Transaction.builder()
                .id(id)
                .amount(request.getAmount())
                .type(request.getType())
                .parentId(request.getParentId())
                .build();

        transactionService.save(id, transaction);

        return ResponseEntity.ok(new StatusResponse("ok",null));
    }

    @Operation(summary = "Obtener IDs por tipo", description = "Retorna todos los IDs de transacciones para el tipo especificado.")
    @GetMapping("/types/{type}")
    public ResponseEntity<List<Long>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(transactionService.getIdsByType(type));
    }

    @Operation(summary = "Suma transitiva", description = "Retorna la suma del monto de la transacción y todos sus descendientes recursivamente.")
    @GetMapping("/sum/{id}")
    public ResponseEntity<SumResponse> getSum(@PathVariable Long id) {
        return ResponseEntity.ok(new SumResponse(transactionService.calculateSum(id)));
    }
}