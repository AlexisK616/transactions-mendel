package com.mendel.transactions.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private Double amount;
    private String type;

    @JsonProperty("parent_id")
    private Long parentId;
}