package com.example.accountservice.services;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.models.Account;
import com.example.accountservice.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public Account createAccount(AccountDTO req) {
        Account account = Account.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .dateOfBirth(convertDateStringToDate(req.getDateOfBirth()))
                .idCardNo(req.getIdCardNo())
                .build();
        return accountRepository.save(account);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
    }

    private Date convertDateStringToDate(String date) {
        return Date.valueOf(date);
    }
}
