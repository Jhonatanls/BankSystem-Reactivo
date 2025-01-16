package com.training.reactive_bank_system.service;

import com.training.reactive_bank_system.model.Transaction;
import com.training.reactive_bank_system.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

//    @Override
//    public Flux<Transaction> streamTransaction(Long accountNumber) {
//        return null;
//    }
}
