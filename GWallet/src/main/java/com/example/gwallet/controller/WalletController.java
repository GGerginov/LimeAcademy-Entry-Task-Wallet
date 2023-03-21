package com.example.gwallet.controller;

import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/wallet", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        List<WalletDto> allWallets = this.walletService.getAllWallets();

        return ResponseEntity.ok(allWallets);
    }

    @GetMapping("/{address}")
    public ResponseEntity<?> getWalletByAddress(@PathVariable String address) {

        WalletDto walletByAddress = this.walletService.getWalletByAddress(address);

        return ResponseEntity.ok(walletByAddress);
    }
}
