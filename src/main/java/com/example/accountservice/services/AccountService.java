package com.example.accountservice.services;

import com.example.accountservice.dto.AccountInput;
import com.example.accountservice.dto.IdCard;
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
    private CryptoClientService cryptoClientService;

    public Account createAccount(AccountInput req) {

        IdCard idCardDTO = new IdCard(req.getIdCardNo());
        String idCard = cryptoClientService.encryptIdCard(idCardDTO);
        Account account = Account.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .dateOfBirth(convertDateStringToDate(req.getDateOfBirth()))
                .idCardNo(idCard)
                .build();
        return accountRepository.save(account);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        Account account = accountRepository.findById(id).get();
        IdCard idCardDTO = new IdCard(account.getIdCardNo());
        String idCard = cryptoClientService.decryptIdCard(idCardDTO);
        account.setIdCardNo(idCard);

        return account;
    }

    private Date convertDateStringToDate(String date) {
        return Date.valueOf(date);
    }
}
