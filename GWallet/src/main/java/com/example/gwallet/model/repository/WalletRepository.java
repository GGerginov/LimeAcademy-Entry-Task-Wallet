package com.example.gwallet.model.repository;

import com.example.gwallet.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Nullable
    Optional<Wallet> findByAddress(String address);
}
