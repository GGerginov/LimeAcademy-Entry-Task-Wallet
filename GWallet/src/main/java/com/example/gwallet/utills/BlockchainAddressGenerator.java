package com.example.gwallet.utills;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class BlockchainAddressGenerator {

    private static final String HEX_CHARACTERS = "0123456789ABCDEF";
    private static final int ADDRESS_LENGTH = 42;

    public String generateAddress() {
        StringBuilder addressBuilder = new StringBuilder("0x");
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < ADDRESS_LENGTH - 2; i++) {
            int randomIndex = random.nextInt(HEX_CHARACTERS.length());
            addressBuilder.append(HEX_CHARACTERS.charAt(randomIndex));
        }

        return addressBuilder.toString();
    }

}
