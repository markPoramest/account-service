package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountInput;
import com.example.accountservice.models.Account;
import com.example.accountservice.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountInput req) {
        Account account = accountService.createAccount(req);

        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping(path = "/search")
    public ResponseEntity<Page<Account>> getAccountsByFirstName(@RequestParam String firstName, Pageable pageable) {
        return ResponseEntity.ok(accountService.getByFirstNameStartWith(firstName, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }
}
