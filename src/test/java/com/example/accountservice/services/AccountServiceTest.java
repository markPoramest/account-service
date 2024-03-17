package com.example.accountservice.services;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.IdCardDTO;
import com.example.accountservice.models.Account;
import com.example.accountservice.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private CryptoClientService cryptoClientService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        cryptoClientService = Mockito.mock(CryptoClientService.class);
        accountRepository = Mockito.mock(AccountRepository.class);

        accountService = new AccountService(accountRepository, cryptoClientService);
    }


    @Test
    void testCreateAccount_whenSuccess_shouldReturnAccount() {
        AccountDTO accountDTO = AccountDTO.builder()
                .firstName("Firstname")
                .lastName("Lastname")
                .email("email@gmail.com")
                .idCardNo("1234567890987")
                .dateOfBirth("2000-01-01")
                .build();

        IdCardDTO idCardDTO = new IdCardDTO(accountDTO.getIdCardNo());

        Account account = Account.builder()
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .email(accountDTO.getEmail())
                .dateOfBirth(Date.valueOf(accountDTO.getDateOfBirth()))
                .idCardNo("encryptedId")
                .build();

        when(cryptoClientService.encryptIdCard(idCardDTO)).thenReturn("encryptedId");
        when(accountRepository.save(account)).thenReturn(Account.builder().id(1L).build());

        Account createdAccount = accountService.createAccount(accountDTO);

        assert createdAccount.getId() == 1L;
    }

    @Test
    void testGetAccountById_whenSuccess_shouldReturnAccount() {
        Account account = Account.builder().id(1L).idCardNo("encryptedId").build();

        when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(account));
        when(cryptoClientService.decryptIdCard(new IdCardDTO("encryptedId"))).thenReturn("1234567890987");

        Account ac = accountService.getAccount(1L);

        assert ac.getId() == 1L;
        assert ac.getIdCardNo().equals("1234567890987");

    }
}
