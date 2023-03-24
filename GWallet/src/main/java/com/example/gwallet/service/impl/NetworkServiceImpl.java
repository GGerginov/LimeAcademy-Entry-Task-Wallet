package com.example.gwallet.service.impl;

import com.example.gwallet.controller.RequestDTOs.TransactionRequestDTO;
import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.TransactionDto;
import com.example.gwallet.model.entity.Transaction;
import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.TransactionRepository;
import com.example.gwallet.service.NetworkService;
import com.example.gwallet.service.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.gwallet.model.jsonMessages.Messages.ErrorMessages.TRANSACTION_FAILED;

/**
 * Service implementation for the network-related functionalities in the GWallet application.
 * This service is responsible for handling network-specific operations, such as
 * creating transactions and calculating fees.
 *
 * @author G.Gerginov
 * @version 1.0
 * @since 1.0
 */
@Service
public class NetworkServiceImpl implements NetworkService {

    private static final Double TRANSACTION_FEE_PERCENT = 0.01;

    private Double fees = 0.00;
    private final WalletService walletService;

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;


    /**
     * Constructor for NetworkServiceImpl.
     *
     * @param walletService         The wallet service to be used
     * @param transactionRepository The transaction repository to be used
     * @param modelMapper           The model mapper to be used
     */
    @Autowired
    public NetworkServiceImpl(WalletService walletService, TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a transaction and updates the wallets of the sender and receiver accordingly.
     *
     * @param transactionRequestDTO The transaction request data transfer object containing the necessary information
     *                              for creating a transaction
     * @return TransactionDto The data transfer object representing the created transaction
     * @throws ApiException if the transaction fails
     */
    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionRequestDTO transactionRequestDTO) throws ApiException {

        Double transactionAmount = transactionRequestDTO.getAmount();
        this.fees += transactionAmount * TRANSACTION_FEE_PERCENT;
        transactionAmount = transactionAmount * (1 - TRANSACTION_FEE_PERCENT);

        boolean isTransactionPassed = this.walletService.makeATransaction(transactionRequestDTO.getSenderAddress()
                , transactionRequestDTO.getReceiverAddress()
                , transactionAmount);

        if (!isTransactionPassed) {
            throw new ApiException(TRANSACTION_FAILED);
        }
        Wallet sender = this.modelMapper.map(
                this.walletService.getWalletByAddress(transactionRequestDTO.getSenderAddress()),
                Wallet.class);

        Wallet receiver = this.modelMapper.map(
                this.walletService.getWalletByAddress(transactionRequestDTO.getReceiverAddress()),
                Wallet.class);

        Transaction transaction = this.transactionRepository.saveAndFlush(new Transaction(sender, receiver, transactionAmount));


        return this.modelMapper.map(transaction, TransactionDto.class);
    }

    public Double getFees() {
        return fees;
    }
}
