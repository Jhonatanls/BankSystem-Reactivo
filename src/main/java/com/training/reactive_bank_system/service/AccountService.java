package com.training.reactive_bank_system.service;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface AccountService {

    Mono<Account> createAccount(Account account);

    Mono<Account> getAccountById(String accountId);

    Flux<BalanceResponseDTO> getRealTimeBalance(String accountId);

    Mono<Void> deleteAccount(String accountId);

}
