package com.training.reactive_bank_system.service;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.dto.TransactionEntryDTO;
import com.training.reactive_bank_system.dto.TransactionMapper;
import com.training.reactive_bank_system.dto.TransactionResponseDTO;
import com.training.reactive_bank_system.exceptions.AccountNotFoundException;
import com.training.reactive_bank_system.exceptions.InvalidTransactionException;
import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.model.Transaction;
import com.training.reactive_bank_system.model.TransactionType;
import com.training.reactive_bank_system.repository.AccountRepository;
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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public Mono<BalanceResponseDTO> createTransaction(TransactionEntryDTO transactionEntryDTO) {

        return validateTransaction(transactionEntryDTO)
                .then(validateTransactionAccount(transactionEntryDTO.getAccountId()))
                .flatMap(account -> updateAccount(account, transactionEntryDTO))
                .flatMap(account -> saveTransaction(account, transactionEntryDTO))
                .map(account -> BalanceResponseDTO.builder()
                        .userId(account.getUserId())
                        .accountId(account.getAccountId())
                        .balance(account.getBalance()).build())
                .onErrorMap(e -> {
                    if(e instanceof IllegalArgumentException){
                        return new InvalidTransactionException(e.getMessage());
                    }
                    return e;
                });

    }

    private Mono<Void> validateTransaction(TransactionEntryDTO transactionEntryDTO){

        if(transactionEntryDTO.getAmount() <= 0) {
            return Mono.error(new InvalidTransactionException("Las operaciones se deben hacer con montos mayores a cero"));
        }
        return Mono.empty();
    }

    private Mono<Account> updateAccount(Account account, TransactionEntryDTO transactionEntryDTO) {
        if (transactionEntryDTO.getTransactionType().equals(TransactionType.RETIRO)) {
            if( transactionEntryDTO.getAmount() > account.getBalance()) {
                return Mono.error(new InvalidTransactionException("El monto de los retiros debe ser menor al saldo de la cuenta"));
            }
            account.setBalance(account.getBalance() - transactionEntryDTO.getAmount());
        }else {
            account.setBalance(account.getBalance() + transactionEntryDTO.getAmount());
        }
        return accountRepository.save(account);
    }

    private Mono<Account> validateTransactionAccount(String accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("La cuenta no existe")));
    }

    private Mono<Account> saveTransaction(Account account, TransactionEntryDTO transactionEntryDTO) {
        return Mono.defer(() -> {
            Transaction transaction = transactionMapper.DTOtoEntity(transactionEntryDTO, account);
            return transactionRepository.save(transaction).thenReturn(account);
        });
    }

    @Override
    public Flux<TransactionResponseDTO> auditTransactions(String accountId) {
        return transactionRepository.findWithTailableCursorByAccountId(accountId)
                .switchIfEmpty(Flux.error(new IllegalArgumentException("La cuenta no tiene transacciones")))
                .map(transaction -> transactionMapper.toExitDTO(transaction));
    }
}
