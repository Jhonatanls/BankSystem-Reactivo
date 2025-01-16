package com.training.reactive_bank_system;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.repository.AccountRepository;
import com.training.reactive_bank_system.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_shouldSaveAccountSuccessfully() {
        // Arrange
        String accountId = "1";
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789L);
        account.setUserId(1010115618L);

        Mockito.when(accountRepository.save(argThat(a -> a.equals(account)))).thenReturn(Mono.just(account));

        // Act
        Mono<Account> result = accountService.createAccount(account);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(savedAccount -> savedAccount.getAccountId().equals(accountId) &&
                        savedAccount.getUserId().equals(1010115618L) &&
                        savedAccount.getAccountNumber().equals(123456789L))
                .verifyComplete();

        verify(accountRepository).save(argThat(a -> a.equals(account)));

    }

    @Test
    void getAccountById_shouldReturnAccountWhenExists() {
        // Arrange
        String accountId = "1";
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789L);
        account.setUserId(1010115618L);

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));

        // Act
        Mono<Account> result = accountService.getAccountById(accountId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(foundAccount -> foundAccount.getAccountId().equals(accountId))
                .verifyComplete();

        verify(accountRepository).findById(accountId);
    }

    @Test
    void getRealTimeBalance_shouldReturnBalanceWhenAccountExists() {
        // Arrange
        String accountId = "1";
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789L);
        account.setUserId(1010115618L);
        when(accountRepository.findById(accountId)).thenReturn(Flux.just(account));

        // Act
        Flux<BalanceResponseDTO> result = accountService.getRealTimeBalance(accountId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(balance -> balance.getAccountId().equals("1") &&
                        balance.getBalance() == 123456789L &&
                        balance.getUserId().equals(1010115618L))
                .verifyComplete();

        verify(accountRepository).findById(accountId);
    }

    @Test
    void deleteAccount_shouldDeleteAccountSuccessfully() {
        // Arrange
        String accountId = "1";

        when(accountRepository.deleteById(accountId)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = accountService.deleteAccount(accountId);

        // Assert
        StepVerifier.create(result)
                .expectComplete() // Explicitly assert completion
                .verify();

        verify(accountRepository).deleteById(accountId);
    }
}
