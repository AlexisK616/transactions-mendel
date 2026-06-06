package com.mendel.transactions.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendel.transactions.dto.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest(5000.0, "cars", null);

        mockMvc.perform(put("/transactions/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    void shouldReturnIdsByType() throws Exception {
        TransactionRequest request = new TransactionRequest(5000.0, "cars", null);

        mockMvc.perform(put("/transactions/20")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(get("/transactions/types/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(20L));
    }

    @Test
    void shouldReturnTransitiveSum() throws Exception {
        mockMvc.perform(put("/transactions/30")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new TransactionRequest(5000.0, "cars", null))));

        mockMvc.perform(put("/transactions/31")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new TransactionRequest(10000.0, "shopping", 30L))));

        mockMvc.perform(put("/transactions/32")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new TransactionRequest(5000.0, "shopping", 31L))));

        mockMvc.perform(get("/transactions/sum/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(20000.0));
    }
}