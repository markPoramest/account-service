package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.models.Account;
import com.example.accountservice.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping()
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountDTO req) {
        Account account = accountService.createAccount(req);

        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }
}
