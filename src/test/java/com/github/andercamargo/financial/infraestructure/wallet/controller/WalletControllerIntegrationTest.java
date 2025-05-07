package com.github.andercamargo.financial.infraestructure.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andercamargo.financial.infrastructure.config.db.repository.CustomerRepository;
import com.github.andercamargo.financial.infrastructure.wallet.dto.WalletInputDTO;
import com.github.andercamargo.financial.usecase.wallet.WalletUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class WalletControllerIntegrationTest {

    @Mock
    CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private WalletInputDTO walletInput;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    WalletUseCase walletUseCase;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        walletInput = new WalletInputDTO("wallet", "customer");
    }

    @Test
    void shouldCreateWalletWithError() throws Exception {
        Mockito.when(customerRepository.findByUserName ("customer")).thenReturn(null);
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(new com.github.andercamargo.financial.infrastructure.config.db.schema.CustomerSchema());
        Mockito.when(customerRepository.findByUserNameAndWallet ("customer", "wallet")).thenReturn(new com.github.andercamargo.financial.infrastructure.config.db.schema.CustomerSchema());
        Mockito.when(walletUseCase.create(Mockito.any())).thenReturn(null);

        mockMvc.perform(post("/v1/api/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletInput)))
                .andExpect(status().isInternalServerError());
    }
}
