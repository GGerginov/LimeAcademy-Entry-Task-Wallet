package com.example.gwallet.controller;

import com.example.gwallet.controller.RequestDTOs.TransactionRequestDTO;
import com.example.gwallet.controller.ResponseDTOs.TransactionResponseDTO;
import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.TransactionDto;
import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.model.jsonMessages.ErrorResponse.ErrorResponse;
import com.example.gwallet.model.jsonMessages.SuccessResponse.SuccessResponse;
import com.example.gwallet.service.NetworkService;
import com.example.gwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class NetworkController {

    private final NetworkService networkService;
    private final WalletService walletService;

    @Autowired
    public NetworkController(NetworkService networkService, WalletService walletService) {
        this.networkService = networkService;
        this.walletService = walletService;
    }

    @PostMapping("/create-wallet")
    public ResponseEntity<?> createWallet() {

        WalletDto newWallet = this.walletService.createNewWallet();

        return new SuccessResponse<>(newWallet).getResponse();
    }

    @PostMapping("/make-transaction")
    public ResponseEntity<?> makeTransaction(@RequestBody @Validated TransactionRequestDTO transactionRequestDTO, Errors errors) {

        if (errors.hasErrors()) {
            return new ErrorResponse(errors).getResponse();
        }

        try {
            TransactionDto transaction = this.networkService.createTransaction(transactionRequestDTO);
            TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO(transaction.getId(),
                    transaction.getAmount(),
                    transaction.getSender().getAddress(),
                    transaction.getReceiver().getAddress());

            return new SuccessResponse<>(transactionResponseDTO).getResponse();
        } catch (ApiException e) {
            return new ErrorResponse(e).getResponse();
        }

    }
}
