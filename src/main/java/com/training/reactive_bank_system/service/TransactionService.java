package com.training.reactive_bank_system.service;

import com.training.reactive_bank_system.model.Transaction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface TransactionService {

    public Mono<Transaction> createTransaction(Transaction transaction);

    //Mono<Transaction> getRealTimeBalance(String accountId);

    //public Flux<Transaction> streamTransaction(Long accountNumber);

}
