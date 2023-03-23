package com.example.gwallet.model.repository;

import com.example.gwallet.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Transactional
    @Modifying
    @Query("update Wallet w set w.balance = ?1 where w.address = ?2")
    int updateWalletAmountByAddress(Double balance, String address);

    @Nullable
    Optional<Wallet> findByAddress(String address);

    @Query("select (count(w) > 0) from Wallet w where w.address = ?1")
    boolean isAddressExist(String address);

}
