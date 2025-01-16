package com.training.reactive_bank_system;

import com.training.reactive_bank_system.controller.AccountController;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(AccountController.class) // Focus on controller testing
class AccountControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private AccountRepository accountRepository;

    @Test
    void createAccount_shouldSaveAccountSuccessfully() {
        // Arrange
        String accountId = "1";
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789L);
        account.setUserId(1010115618L);

        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(Mono.just(account));

        // Act & Assert
        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(account)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Account.class)
                .value(savedAccount -> {
                    assert savedAccount.getAccountId().equals(accountId);
                    assert savedAccount.getAccountNumber().equals(123456789L);
                    assert savedAccount.getUserId().equals(1010115618L);
                });

        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
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

        // Act & Assert
        webTestClient.get()
                .uri("/accounts/{id}", accountId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(foundAccount -> {
                    assert foundAccount.getAccountId().equals(accountId);
                });

        Mockito.verify(accountRepository).findById(accountId);
    }

    @Test
    void getRealTimeBalance_shouldReturnBalanceWhenAccountExists() {
        // Arrange
        String accountId = "1";
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789L);
        account.setUserId(1010115618L);

        BalanceResponseDTO balanceResponse = new BalanceResponseDTO(account.getUserId(),accountId,  account.getBalance());

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));

        // Act & Assert
        webTestClient.get()
                .uri("/accounts/{id}/balance", accountId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BalanceResponseDTO.class)
                .value(balance -> {
                    assert balance.getAccountId().equals(accountId);
                    assert balance.getUserId().equals(1010115618L);
                    assert balance.getBalance() == 123456789L;
                });

        Mockito.verify(accountRepository).findById(accountId);
    }

    @Test
    void deleteAccount_shouldDeleteAccountSuccessfully() {
        // Arrange
        String accountId = "1";

        Mockito.when(accountRepository.deleteById(accountId)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.delete()
                .uri("/accounts/{id}", accountId)
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(accountRepository).deleteById(accountId);
    }
}

