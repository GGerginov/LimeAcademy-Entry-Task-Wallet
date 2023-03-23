package com.example.gwallet.controller;

import com.example.gwallet.controller.RequestDTOs.TransactionRequestDTO;
import com.example.gwallet.model.DTOs.TransactionDto;
import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.service.NetworkService;
import com.example.gwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createWallet(){

        WalletDto newWallet = this.walletService.createNewWallet();
        return ResponseEntity.ok(newWallet);
    }

    @PostMapping("/make-transaction")
    public ResponseEntity<?> makeTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) throws Exception {

        TransactionDto transaction = this.networkService.createTransaction(transactionRequestDTO);
        return ResponseEntity.ok(transaction);
    }
}
