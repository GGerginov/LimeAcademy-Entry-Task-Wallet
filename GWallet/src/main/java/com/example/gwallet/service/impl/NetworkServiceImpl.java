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


@Service
public class NetworkServiceImpl implements NetworkService {

    private static final Double TRANSACTION_FEE_PERCENT = 0.01;

    private Double fees = 0.00;
    private final WalletService walletService;

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public NetworkServiceImpl(WalletService walletService, TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

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


}
