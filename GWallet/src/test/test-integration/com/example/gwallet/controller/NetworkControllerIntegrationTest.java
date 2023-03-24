package com.example.gwallet.controller;

import com.example.gwallet.model.entity.Wallet;
import com.example.gwallet.model.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql({ "/drop_schema.sql", "/create_schema.sql" })
@Sql(scripts = "/insert_data.sql")
class NetworkControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    WalletRepository walletRepository;

    private String validAddress1;

    private String validAddress2;

    private Double validAmount;

    private static final String CREATE_WALLET_URL = "/api/v1/create-wallet";
    private static final String MAKE_TRANSACTION_URL = "/api/v1/make-transaction";
    private static final Double MOCK_AMOUNT = 100.00;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        List<Wallet> all = this.walletRepository.findAll();
        this.validAddress1 = all.get(0).getAddress();
        this.validAddress2 = all.get(1).getAddress();
        this.validAmount = 1.0;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    void testCreateWallet() throws Exception {

        this.mockMvc.perform(post(CREATE_WALLET_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.address",hasLength(42)))
                .andExpect(jsonPath("$.data.balance",is(100.0)));
    }

    @Test
    public void makeATransactionShouldPass() throws Exception {

        String requestBody = new JSONObject()
                .put("sender_address", validAddress1)
                .put("receiver_address", validAddress2)
                .put("amount", this.validAmount)
                .toString();

        this.mockMvc.perform(post(MAKE_TRANSACTION_URL)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.transaction_id",notNullValue()))
                .andExpect(jsonPath("$.data.transaction_amount",is(this.validAmount*0.99)))
                .andExpect(jsonPath("$.data.sender_address",is(this.validAddress1)))
                .andExpect(jsonPath("$.data.receiver_address",is(this.validAddress2)));
    }

    @Test
    public void makeATransactionWithNegativeAmountShouldTrow() throws Exception {

        String requestBody = new JSONObject()
                .put("sender_address", validAddress1)
                .put("receiver_address", validAddress2)
                .put("amount", -1)
                .toString();

        this.mockMvc.perform(post(MAKE_TRANSACTION_URL)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors.[0].code",is(400)))
                .andExpect(jsonPath("$.errors.[0].message",is("Amount can not be negative or zero !")));
    }

    @Test
    public void makeATransactionWithUnavailableAddressShouldThrow() throws Exception {


        String requestBody = new JSONObject()
                .put("sender_address", "Invalid")
                .put("receiver_address", validAddress2)
                .put("amount", this.validAmount)
                .toString();

        this.mockMvc.perform(post(MAKE_TRANSACTION_URL)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.errors.[0].code",is(400)))
                .andExpect(jsonPath("$.errors.[0].message",is("Address is not exist!")));
    }


    //TODO write more tests
}