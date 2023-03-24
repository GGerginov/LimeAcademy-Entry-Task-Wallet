package com.example.gwallet.service.impl;

import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.gwallet.model.jsonMessages.Messages.ErrorMessages.SENDER_DOES_NOT_HAVE_ENOUGH_BALANCE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplUnitTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    private Wallet wallet1;
    private Wallet wallet2;
    private WalletDto walletDto1;
    private WalletDto walletDto2;

    @BeforeEach
    void setUp() {
        wallet1 = new Wallet("0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e901");
        wallet1.setBalance(100.0);

        wallet2 = new Wallet("0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e902");
        wallet2.setBalance(200.0);

        walletDto1 = new WalletDto(wallet1.getAddress(), wallet1.getBalance());
        walletDto2 = new WalletDto(wallet2.getAddress(), wallet2.getBalance());
    }

    @Test
    void testGetAllWallets() {
        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);
        when(walletRepository.findAll()).thenReturn(wallets);
        when(modelMapper.map(wallets, new TypeToken<List<WalletDto>>() {
        }.getType()))
                .thenReturn(Arrays.asList(walletDto1, walletDto2));

        List<WalletDto> result = walletService.getAllWallets();


        assertEquals(wallets.size(), result.size());

        for (int i = 0; i < wallets.size(); i++) {

            Wallet wallet = wallets.get(i);
            WalletDto walletDto = result.get(i);

            assertEquals(wallet.getAddress(), walletDto.getAddress());
            assertEquals(wallet.getBalance(), walletDto.getBalance());
            assertEquals(wallet.getId(), walletDto.getId());
        }
    }

    @Test
    void testGetWalletByAddress() throws ApiException {
        when(walletRepository.findByAddress(wallet1.getAddress())).thenReturn(Optional.of(wallet1));
        when(modelMapper.map(wallet1, WalletDto.class)).thenReturn(walletDto1);

        WalletDto result = walletService.getWalletByAddress(wallet1.getAddress());

        assertEquals(walletDto1, result);
    }

    @Test
    public void testIsAddressExist_true() {
        String address = "0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e901";
        when(walletRepository.isAddressExist(address)).thenReturn(true);

        boolean result = walletService.isAddressExist(address);

        assertTrue(result);
    }

    @Test
    public void testIsAddressExist_false() {
        String address = "0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e999";
        when(walletRepository.isAddressExist(address)).thenReturn(false);

        boolean result = walletService.isAddressExist(address);

        assertFalse(result);
    }


    @Test
    public void testMakeATransaction_success() throws ApiException {
        String senderAddress = "0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e901";
        String receiverAddress = "0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e902";
        Double amount = 50.0;

        when(walletRepository.findByAddress(senderAddress)).thenReturn(Optional.of(wallet1));
        when(walletRepository.findByAddress(receiverAddress)).thenReturn(Optional.of(wallet2));
        when(modelMapper.map(wallet1, WalletDto.class)).thenReturn(walletDto1);
        when(modelMapper.map(wallet2, WalletDto.class)).thenReturn(walletDto2);

        when(walletRepository.updateWalletAmountByAddress(walletDto1.getBalance() - amount, senderAddress)).thenReturn(1);
        when(walletRepository.updateWalletAmountByAddress(walletDto2.getBalance() + amount, receiverAddress)).thenReturn(1);

        boolean result = walletService.makeATransaction(senderAddress, receiverAddress, amount);

        assertTrue(result);
    }

    @Test
    public void testMakeATransaction_insufficientBalance() {
        String senderAddress = "0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e901";
        String receiverAddress = "0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e902";
        Double amount = 150.0;

        when(walletRepository.findByAddress(senderAddress)).thenReturn(Optional.of(wallet1));
        when(walletRepository.findByAddress(receiverAddress)).thenReturn(Optional.of(wallet2));
        when(modelMapper.map(wallet1, WalletDto.class)).thenReturn(walletDto1);
        when(modelMapper.map(wallet2, WalletDto.class)).thenReturn(walletDto2);

        ApiException exception = assertThrows(ApiException.class,
                () -> walletService.makeATransaction(senderAddress, receiverAddress, amount));

        assertEquals(SENDER_DOES_NOT_HAVE_ENOUGH_BALANCE.getMessage(), exception.getMessage());

    }

    @Test
    public void testCreateNewWallet() {
        Wallet createdWallet = new Wallet("0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e903");
        WalletDto createdWalletDto = new WalletDto("0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e903", 0.0);

        when(walletRepository.saveAndFlush(any(Wallet.class))).thenReturn(createdWallet);
        when(modelMapper.map(createdWallet, WalletDto.class)).thenReturn(createdWalletDto);

        WalletDto result = walletService.createNewWallet();

        assertNotNull(result);
        assertEquals(createdWalletDto.getAddress(), result.getAddress());
        assertEquals(createdWalletDto.getBalance(), result.getBalance());
    }


}


