package com.example.gwallet.service.impl;

import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.WalletRepository;
import com.example.gwallet.service.WalletService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql({"/drop_schema.sql", "/create_schema.sql"})
@Sql(scripts = "/insert_data.sql")
class WalletServiceImplTest {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    @Test
    @Transactional
    void getAllWalletsShouldReturnAllWallets() {

        List<WalletDto> expectedWallets = this.walletService.getAllWallets();

        List<Wallet> actualWallets = this.walletRepository.findAll();

        assertEquals(expectedWallets.size(), actualWallets.size());


        for (int i = 0; i < actualWallets.size(); i++) {

            WalletDto currentExpected = expectedWallets.get(i);
            Wallet currentActual = actualWallets.get(i);

            assertEquals(currentExpected.getAddress(), currentActual.getAddress());
            assertEquals(currentExpected.getBalance(), currentActual.getBalance());
            assertEquals(currentExpected.getId(), currentActual.getId());
        }
    }

    @Test
    @Transactional
    void getWalletByAddressShouldPass() throws ApiException {

        Wallet actualWallet = this.walletRepository.findAll().get(0);

        WalletDto expectedWallet = this.walletService.getWalletByAddress(actualWallet.getAddress());

        assertEquals(expectedWallet.getAddress(), actualWallet.getAddress());
        assertEquals(expectedWallet.getBalance(), actualWallet.getBalance());
        assertEquals(expectedWallet.getId(), actualWallet.getId());
    }


    @Test
    void getWalletByAddressShouldTrow() throws ApiException {

        assertThrows(ApiException.class, () -> this.walletService.getWalletByAddress("INVALID"));
    }

    @Test
    void isAddressExistShouldPass() {

        Wallet existingWallet = this.walletRepository.findAll().get(0);

        assertTrue(this.walletService.isAddressExist(existingWallet.getAddress()));
    }

    @Test
    void isAddressExistShouldTrow() {
        assertFalse(this.walletService.isAddressExist("INVALID"));
    }

    @Test
    void makeTransactionShouldTransferBalanceFromSenderToReviewer() throws ApiException {

        Wallet sender = this.walletRepository.findAll().get(0);
        Wallet receiver = this.walletRepository.findAll().get(1);


        boolean isPassed = this.walletService.makeATransaction(sender.getAddress(), receiver.getAddress(), 1.09);

        assertTrue(isPassed);
        assertTrue(sender.getBalance() > this.walletRepository.findById(sender.getId()).get().getBalance());
        assertTrue(receiver.getBalance() < this.walletRepository.findById(receiver.getId()).get().getBalance());
    }

    @Test
    void makeTransactionWhenSenderDoesNotHaveEnoughBalanceShouldTrow() throws Exception {

        Wallet sender = this.walletRepository.findAll().get(0);
        Wallet receiver = this.walletRepository.findAll().get(1);

        boolean isPassed = this.walletService.makeATransaction(sender.getAddress(), receiver.getAddress(), 1.09);

        assertThrows(ApiException.class, () -> this.walletService.makeATransaction(sender.getAddress(), receiver.getAddress(), sender.getBalance() + 1));
    }

    @Test
    void createWalletShouldAddNewWallet() {

        long count = this.walletRepository.count();

        WalletDto newWallet = this.walletService.createNewWallet();

        assertNotNull(newWallet);
        assertEquals(count+1,this.walletRepository.count());
    }
}