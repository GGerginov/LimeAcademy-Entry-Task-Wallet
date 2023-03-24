package com.example.gwallet.service.impl;

import com.example.gwallet.controller.RequestDTOs.TransactionRequestDTO;
import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.TransactionDto;
import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.TransactionRepository;
import com.example.gwallet.model.repository.WalletRepository;
import com.example.gwallet.service.NetworkService;
import com.example.gwallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql({ "/drop_schema.sql", "/create_schema.sql" })
@Sql(scripts = "/insert_data.sql")
class NetworkServiceImplIntegrationTest {

    @Autowired
    private NetworkService networkService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Double TRANSACTION_FEE_PERCENT = 0.01;

    private Wallet sender;
    private Wallet receiver;

    private TransactionRequestDTO transactionRequestDTO;

    private Double transactionAmount;

    @BeforeEach
    public void setUp() {
        List<Wallet> all = walletRepository.findAll();
        sender = all.get(0);
        receiver = all.get(1);

        this.transactionAmount = 20.0;

        this.transactionRequestDTO = new TransactionRequestDTO(
                sender.getAddress(),
                receiver.getAddress(),
                transactionAmount);
    }

    @Test
    void createTransactionShouldPass() throws ApiException {

        TransactionDto transactionDto = networkService.createTransaction(this.transactionRequestDTO);

        assertNotNull(transactionDto);
        assertEquals(sender.getAddress(), transactionDto.getSender().getAddress());
        assertEquals(receiver.getAddress(), transactionDto.getReceiver().getAddress());
        assertEquals(transactionAmount * (1 - TRANSACTION_FEE_PERCENT), transactionDto.getAmount());
        assertTrue(transactionRepository.existsById(transactionDto.getId()));
    }

    @Test
    void createTransactionShouldTrowIfSenderDoesNotHaveEnoughBalance() throws Exception {

        TransactionRequestDTO invalidTransactionDto = new TransactionRequestDTO(transactionRequestDTO.getSenderAddress(),
                transactionRequestDTO.getReceiverAddress(),Double.MAX_VALUE);

        assertThrows(ApiException.class,() -> this.networkService.createTransaction(invalidTransactionDto));
    }

}
