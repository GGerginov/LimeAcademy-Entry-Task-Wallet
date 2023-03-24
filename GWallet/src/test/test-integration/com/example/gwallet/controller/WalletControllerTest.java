package com.example.gwallet.controller;

import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql({ "/drop_schema.sql", "/create_schema.sql" })
@Sql(scripts = "/insert_data.sql")
class WalletControllerTest {

    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;

    String endpoint;
    @Autowired
    private WalletRepository walletRepository;

    private List<Wallet> allWallets;


    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.endpoint = "/wallet/";
        this.allWallets = this.walletRepository.findAll();

        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllShouldPass() throws Exception {

        this.mockMvc.perform(get(endpoint+"/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data[0].wallet_address", is("0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e942")))
                .andExpect(jsonPath("$.data[0].wallet_balance", is(98.019821799999990000000000000000)))
                .andExpect(jsonPath("$.data[1].wallet_address", is("0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e981")))
                .andExpect(jsonPath("$.data[1].wallet_balance", is(100.000000000000000000000000000000)))
                .andExpect(jsonPath("$.data[2].wallet_address", is("0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e970")))
                .andExpect(jsonPath("$.data[2].wallet_balance", is(101.980178200000010000000000000000)));
    }


    @Test
    void getWalletByAddressShouldReturnWallet() throws Exception {

        Wallet wantedWallet = allWallets.get(0);


        this.mockMvc.perform(get(this.endpoint+wantedWallet.getAddress()))
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.address", is(wantedWallet.getAddress())))
                .andExpect(jsonPath("$.data.balance", is(wantedWallet.getBalance())));
    }

    @Test
    public void getWalletByInvalidAddress() throws Exception {

        this.mockMvc.perform(get(endpoint+"Invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors.[0].code",is(404)))
                .andExpect(jsonPath("$.errors.[0].message",is("Wallet not found")));
    }
}