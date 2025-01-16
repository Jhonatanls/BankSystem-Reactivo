package com.training.reactive_bank_system.service;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.dto.TransactionEntryDTO;
import com.training.reactive_bank_system.dto.TransactionResponseDTO;
import com.training.reactive_bank_system.model.Transaction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface TransactionService {


    public Mono<BalanceResponseDTO> createTransaction(TransactionEntryDTO transactionEntryDTO);

    public Flux<TransactionResponseDTO> auditTransactions(String accountId);

}
