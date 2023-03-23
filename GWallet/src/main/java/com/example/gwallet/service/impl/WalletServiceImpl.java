package com.example.gwallet.service.impl;

import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.WalletRepository;
import com.example.gwallet.service.WalletService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

import static com.example.gwallet.model.jsonMessages.Messages.ErrorMessages.*;

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

        Type walletDtoType = new TypeToken<List<WalletDto>>() {
        }.getType();


        return this.modelMapper.map(wallets, walletDtoType);
    }

    @Override
    public WalletDto getWalletByAddress(String address) throws ApiException {

        Wallet wallet = getWalletByAddressOrThrowException(address);

        return this.modelMapper.map(wallet, WalletDto.class);
    }

    @Override
    public boolean isAddressExist(String address) {
        return this.walletRepository.isAddressExist(address);
    }

    @Override
    @Transactional
    public boolean makeATransaction(String senderAddress, String receiverAddress, Double amount) throws ApiException {

        WalletDto sender = this.getWalletByAddress(senderAddress);
        WalletDto receiver = this.getWalletByAddress(receiverAddress);

        if (isValid(amount, sender)) {

            boolean senderFlag = walletRepository.updateWalletAmountByAddress(sender.getBalance() - amount
                    , sender.getAddress()) == 1;

            boolean receiverFlag = walletRepository.updateWalletAmountByAddress(receiver.getBalance() + amount
                    , receiver.getAddress()) == 1;

            if (!senderFlag && !receiverFlag) {
               throw new ApiException(TRANSACTION_FAILED);
            }
            return true;
        } else {
            throw new ApiException(SENDER_DOES_NOT_HAVE_ENOUGH_BALANCE);
        }
    }

    @Override
    public WalletDto createNewWallet() {

        //TODO think about better address generation
        Random random = new Random();
        String address = "0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e9" + random.nextInt(0, 9) + random.nextInt(0, 9);
        Wallet wallet = new Wallet(address);

        return this.modelMapper.map(this.walletRepository.saveAndFlush(wallet), WalletDto.class);
    }


    private static boolean isValid(Double amount, WalletDto sender) {
        return sender.getBalance() >= amount;
    }

    private Wallet getWalletByAddressOrThrowException(String address) throws ApiException {
        return this.walletRepository.findByAddress(address).orElseThrow(() -> new ApiException(WALLET_NOT_FOUND));
    }

}
