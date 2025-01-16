package com.training.reactive_bank_system.repository;

import com.training.reactive_bank_system.model.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findByAccountNumber(Long accountNumber);

}
