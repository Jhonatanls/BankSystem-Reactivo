package com.training.reactive_bank_system.controller;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.dto.TransactionEntryDTO;
import com.training.reactive_bank_system.dto.TransactionResponseDTO;
import com.training.reactive_bank_system.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BalanceResponseDTO> createTransaction(@RequestBody TransactionEntryDTO transactionEntryDTO){
        return transactionService.createTransaction(transactionEntryDTO);
    }

    @GetMapping(value = "/history/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<TransactionResponseDTO> getAuditAccount(@PathVariable String accountId) {
        return transactionService.auditTransactions(accountId);
    }

}
