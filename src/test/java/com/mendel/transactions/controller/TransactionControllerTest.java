package com.mendel.transactions.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.transactions.dto.TransactionRequest;
import com.mendel.transactions.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void shouldCreateTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest(5000.0, "cars", null);

        doNothing().when(transactionService).save(eq(10L), any());

        mockMvc.perform(put("/transactions/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    void shouldReturnIdsByType() throws Exception {
        when(transactionService.getIdsByType("cars")).thenReturn(List.of(10L));

        mockMvc.perform(get("/transactions/types/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(10L));
    }

    @Test
    void shouldReturnTransactiveSum() throws Exception {
        when(transactionService.calculateSum(10L)).thenReturn(20000.0);

        mockMvc.perform(get("/transactions/sum/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(20000.0));
    }
}