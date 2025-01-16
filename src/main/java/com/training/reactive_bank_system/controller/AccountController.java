package com.training.reactive_bank_system.controller;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.dto.TransactionResponseDTO;
import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.service.AccountService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    @GetMapping(value = "/balance/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<BalanceResponseDTO> getRealTimeBalance(@PathVariable String accountId){
        return accountService.getRealTimeBalance(accountId);
    }
}
