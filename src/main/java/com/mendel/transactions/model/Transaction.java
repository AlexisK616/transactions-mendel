package com.mendel.transactions.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private Double amount;
    private String type;
    private Long parentId;
}