package com.example.gwallet.service.impl;

import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.WalletRepository;
import com.example.gwallet.service.WalletService;
import com.example.gwallet.utills.BlockchainAddressGenerator;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

import static com.example.gwallet.model.jsonMessages.Messages.ErrorMessages.*;

/**
 * Service implementation for the wallet-related functionalities in the GWallet application.
 *  This service is responsible for handling wallet-specific operations, such as
 *  retrieving all wallets, getting a wallet by its address, checking if an address exists,
 *  making transactions, and creating new wallets.
 *  @author G.Gerginov
 *  @version 1.0
 *  @since 1.0
 */
@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final ModelMapper modelMapper;

    private final BlockchainAddressGenerator generator;

    /**
     * Constructor for WalletServiceImpl.
     *
     * @param walletRepository The wallet repository to be used
     * @param modelMapper      The model mapper to be used
     * @param generator
     */
    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, ModelMapper modelMapper, BlockchainAddressGenerator generator) {
        this.walletRepository = walletRepository;
        this.modelMapper = modelMapper;
        this.generator = generator;
    }


    /**
     * Returns a list of all wallets in the system.
     *
     * @return List<WalletDto> The list of wallet data transfer objects
     */
    @Override
    public List<WalletDto> getAllWallets() {

        List<Wallet> wallets = this.walletRepository.findAll();

        Type walletDtoType = new TypeToken<List<WalletDto>>() {
        }.getType();


        return this.modelMapper.map(wallets, walletDtoType);
    }

    /**
     * Returns a wallet data transfer object by its address.
     *
     * @param address The wallet's address
     * @return WalletDto The wallet data transfer object
     * @throws ApiException if the wallet is not found
     */
    @Override
    public WalletDto getWalletByAddress(String address) throws ApiException {

        Wallet wallet = getWalletByAddressOrThrowException(address);

        return this.modelMapper.map(wallet, WalletDto.class);
    }

    /**
     * Checks if a wallet address exists in the system.
     *
     * @param address The wallet's address
     * @return boolean True if the address exists, false otherwise
     */
    @Override
    public boolean isAddressExist(String address) {
        return this.walletRepository.isAddressExist(address);
    }

    /**
     * Makes a transaction between two wallets, updating their respective balances.
     *
     * This method checks if the transaction is valid, updates the sender's and receiver's
     * balances, and returns true if the transaction is successful. It throws an ApiException
     * if the transaction fails or if the sender does not have enough balance.
     *
     * @param senderAddress   The address of the sender's wallet
     * @param receiverAddress The address of the receiver's wallet
     * @param amount          The amount to be transferred
     * @return boolean        True if the transaction is successful, false otherwise
     * @throws ApiException   If the transaction fails or the sender does not have enough balance
     */
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

    /**
     * Creates a new wallet with a randomly generated address and saves it to the repository.
     *
     * This method generates a new wallet address with a unique blockchain address
     * creates a Wallet object with the generated address, saves it to the repository, and returns a
     * WalletDto object representing the created wallet. Future improvements could involve implementing
     * a more robust address generation mechanism.
     *
     * @return WalletDto The wallet data transfer object representing the newly created wallet
     */
    @Override
    public WalletDto createNewWallet() {

        String address = this.generator.generateAddress();
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
