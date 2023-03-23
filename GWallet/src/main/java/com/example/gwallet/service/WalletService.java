package com.example.gwallet.service;

import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.WalletDto;

import java.util.List;

public interface WalletService {

    List<WalletDto> getAllWallets();

    WalletDto getWalletByAddress(String address) throws ApiException;

    boolean isAddressExist(String address);

    boolean makeATransaction(String senderAddress,String receiverAddress,Double amount) throws ApiException;

    WalletDto createNewWallet();
}
