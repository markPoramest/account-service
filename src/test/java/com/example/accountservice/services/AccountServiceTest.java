package com.example.accountservice.services;

import com.example.accountservice.dto.AccountInput;
import com.example.accountservice.dto.IdCard;
import com.example.accountservice.models.Account;
import com.example.accountservice.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
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
        AccountInput accountInput = AccountInput.builder()
                .firstName("Firstname")
                .lastName("Lastname")
                .email("email@gmail.com")
                .idCardNo("1234567890987")
                .dateOfBirth("2000-01-01")
                .build();

        IdCard idCard = new IdCard(accountInput.getIdCardNo());

        Account account = Account.builder()
                .firstName(accountInput.getFirstName())
                .lastName(accountInput.getLastName())
                .email(accountInput.getEmail())
                .dateOfBirth(Date.valueOf(accountInput.getDateOfBirth()))
                .idCardNo("encryptedId")
                .build();

        when(cryptoClientService.encryptIdCard(idCard)).thenReturn("encryptedId");
        when(accountRepository.save(account)).thenReturn(Account.builder().id(1L).build());

        Account createdAccount = accountService.createAccount(accountInput);

        verify(accountRepository, Mockito.times(1)).save(account);
        verify(cryptoClientService, Mockito.times(1)).encryptIdCard(idCard);
        assert createdAccount.getId() == 1L;
    }

    @Test
    void testGetAccountById_whenSuccess_shouldReturnAccount() {
        Account account = Account.builder().id(1L).idCardNo("encryptedId").build();

        when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(account));
        when(cryptoClientService.decryptIdCard(new IdCard("encryptedId"))).thenReturn("1234567890987");

        Account ac = accountService.getAccount(1L);


        verify(accountRepository, Mockito.times(1)).findById(1L);
        verify(cryptoClientService, Mockito.times(1)).decryptIdCard(new IdCard("encryptedId"));
        assert ac.getId() == 1L;
        assert ac.getIdCardNo().equals("1234567890987");
    }

    @Test
    void testGetAccounts_whenSuccess_shouldReturnAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of(Account.builder().id(1L).build()));

        List<Account> accounts = accountService.getAccounts();

        verify(accountRepository, Mockito.times(1)).findAll();
        assert accounts.get(0).getId() == 1L;
    }

    @Test
    void testGetAccountsByFirstName_whenSuccess_shouldReturnAccounts() {
        Pageable page = PageRequest.of(2, 3);

        Page<Account> mockAccounts = new PageImpl<>(List.of(Account.builder().id(1L)
                .firstName("S")
                .lastName("S")
                .email("S")
                .idCardNo("S")
                .dateOfBirth(Date.valueOf("2023-12-12"))
                .build()));

        when(accountRepository.findByFirstNameStartsWith("S",page)).thenReturn(mockAccounts);

        Page<Account> accounts = accountService.getByFirstNameStartWith("S", page);

        assert accounts.getContent().get(0).getId() == 1L;
        verify(accountRepository, Mockito.times(1)).findByFirstNameStartsWith("S", page);
    }
}
