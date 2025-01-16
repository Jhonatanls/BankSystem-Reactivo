package com.training.reactive_bank_system.controller;

import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.service.AccountService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    @PostMapping("/")
    public Mono<Account> createAccount(@RequestBody Account account){
        return accountService.createAccount(account);
    }

    @GetMapping("/{accountId}")
    public Mono<Account> getAccountById(@PathVariable String accountId) {
        return accountService.getAccountById(accountId);
    }

    @DeleteMapping("/{accountId}")
    public Mono<Void> deleteAccount(@PathVariable String accountId){
        return accountService.deleteAccount(accountId);
    }

}
