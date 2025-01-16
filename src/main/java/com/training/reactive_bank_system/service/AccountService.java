package com.training.reactive_bank_system.service;

import com.training.reactive_bank_system.model.Account;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;


public interface AccountService {

    Mono<Account> createAccount(Account account);

    Mono<Account> getAccountById(String accountId);

    Mono<Void> deleteAccount(String accountId);


}
