package com.example.gwallet.controller;

import com.example.gwallet.controller.ResponseDTOs.WalletResponseDTO;
import com.example.gwallet.exceptions.ApiException;
import com.example.gwallet.model.DTOs.WalletDto;
import com.example.gwallet.model.jsonMessages.ErrorResponse.ErrorResponse;
import com.example.gwallet.model.jsonMessages.SuccessResponse.SuccessResponse;
import com.example.gwallet.model.jsonMessages.SuccessResponse.SuccessResponseBody;
import com.example.gwallet.service.WalletService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(path = "/wallet", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletController {

    private final WalletService walletService;
    private final ModelMapper modelMapper;

    @Autowired
    public WalletController(WalletService walletService, ModelMapper modelMapper) {
        this.walletService = walletService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        List<WalletDto> allWallets = this.walletService.getAllWallets();
        Type walletResponseDtoType = new TypeToken<List<WalletResponseDTO>>() {
        }.getType();
        return new SuccessResponse<>(this.modelMapper.map(allWallets,walletResponseDtoType)).getResponse();
    }

    @GetMapping("/{address}")
    public ResponseEntity<?> getWalletByAddress(@PathVariable String address) {

        try {
            return new SuccessResponse<>(this.walletService.getWalletByAddress(address)).getResponse();
        } catch (ApiException e) {
            return new ErrorResponse(e).getResponse();
        }
    }
}
