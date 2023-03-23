package com.example.gwallet.service;

import com.example.gwallet.controller.RequestDTOs.TransactionRequestDTO;
import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.TransactionDto;

public interface NetworkService {

    TransactionDto createTransaction(TransactionRequestDTO transactionRequestDTO) throws ApiException;

}
