package com.training.reactive_bank_system.service;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Data
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Override
    public Mono<Account> createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> getAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Flux<BalanceResponseDTO> getRealTimeBalance(String accountId) {
        return accountRepository.getTailableBalanceByAccountId(accountId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no encontrada")))
                .map(account -> BalanceResponseDTO.builder()
                        .userId(account.getUserId())
                        .accountId(accountId)
                        .balance(account.getBalance())
                        .build());
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        return accountRepository.deleteById(accountId);
    }
}
