package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.models.Account;
import com.example.accountservice.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AccountController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;


    @Test
    void testCreateAccount_whenCreateSuccess_shouldReturnCreated() throws Exception {
        AccountDTO accountDTO = AccountDTO.builder()
                .firstName("Firstname")
                .lastName("Lastname")
                .email("email@gmail.com")
                .idCardNo("idCardNo")
                .dateOfBirth("2023-12-12")
                .build();

        Account mockAccount = Account.builder()
                .id(1L)
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .email(accountDTO.getEmail())
                .idCardNo(accountDTO.getIdCardNo())
                .dateOfBirth(Date.valueOf(accountDTO.getDateOfBirth()))
                .build();

        given(accountService.createAccount(accountDTO)).willReturn(mockAccount);

        ResultActions result = mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)));

        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockAccount)));
    }

    @Test
    void testCreateAccount_WhenRequestInvalid_ShouldReturnBadRequest() throws Exception {
        AccountDTO accountDTO = AccountDTO.builder()
                .firstName("Firstname")
                .email("email@gmail.com")
                .idCardNo("idCardNo")
                .dateOfBirth("2023-12-12")
                .build();

        ResultActions result = mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.content().json("{'message':'Last name cannot be null'}"));
    }

    @Test
    void testGetAccounts_whenGetSuccess_shouldReturnOk() throws Exception {
        given(accountService.getAccounts()).willReturn(List.of(Account.builder().id(1L).build()));

        ResultActions result = mockMvc.perform(get("/account")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.content().json("[{'id':1}]"));
    }

    @Test
    void testGetAccountById_whenGetSuccess_shouldReturnOk() throws Exception {
        given(accountService.getAccount(1L)).willReturn(Account.builder().id(1L).build());

        ResultActions result = mockMvc.perform(get("/account/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.content().json("{'id':1}"));

    }
}

