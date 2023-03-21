package com.example.gwallet.service.impl;

import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.WalletRepository;
import com.example.gwallet.service.WalletService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, ModelMapper modelMapper) {
        this.walletRepository = walletRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WalletDto> getAllWallets() {

        List<Wallet> wallets = this.walletRepository.findAll();

        Type avatarDtoType = new TypeToken<List<WalletDto>>() {
        }.getType();


        return this.modelMapper.map(wallets, avatarDtoType);
    }

    @Override
    public WalletDto getWalletByAddress(String address) {


        //TODO
        Wallet wallet = walletRepository.findByAddress(address).orElseThrow();

        return this.modelMapper.map(wallet, WalletDto.class);
    }
}
