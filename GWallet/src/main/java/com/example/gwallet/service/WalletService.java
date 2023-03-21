package com.example.gwallet.service;

import com.example.gwallet.model.DTOs.WalletDto;

import java.util.List;

public interface WalletService {

    List<WalletDto> getAllWallets();

    WalletDto getWalletByAddress(String address);
}
