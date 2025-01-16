package com.training.reactive_bank_system.repository;

import com.training.reactive_bank_system.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    @Tailable
    Flux<Transaction> findWithTailableCursorByAccountId(String accountId);
}
