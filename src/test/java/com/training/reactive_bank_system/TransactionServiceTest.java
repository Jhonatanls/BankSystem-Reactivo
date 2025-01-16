package com.training.reactive_bank_system;

import com.training.reactive_bank_system.controller.TransactionController;
import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.dto.TransactionEntryDTO;
import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.model.Transaction;
import com.training.reactive_bank_system.model.TransactionType;
import com.training.reactive_bank_system.repository.AccountRepository;
import com.training.reactive_bank_system.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(TransactionController.class) // Test the TransactionController
class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private TransactionRepository transactionRepository;

    @MockitoBean
    private AccountRepository accountRepository;

    @Test
    void createTransaction_shouldReturnBalanceResponseDTO_onValidTransaction() {
        // Arrange
        String accountId = "1";
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789L);
        account.setUserId(1010115618L);

        TransactionEntryDTO transactionEntryDTO = new TransactionEntryDTO(accountId, 100L, 200.0, TransactionType.DEPOSITO);
        BalanceResponseDTO balanceResponseDTO = new BalanceResponseDTO(account.getUserId(),accountId,  300.0);

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(Mono.just(account));
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(Mono.just(new Transaction()));

        // Act & Assert
        webTestClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionEntryDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BalanceResponseDTO.class)
                .value(response -> {
                    assert response.getAccountId().equals(accountId);
                    assert response.getBalance() == 300.0;
                });

        Mockito.verify(accountRepository).findById(accountId);
        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verify(transactionRepository).save(Mockito.any(Transaction.class));
    }

    @Test
    void createTransaction_shouldThrowInvalidTransactionException_onNegativeAmount() {
        // Arrange
        TransactionEntryDTO transactionEntryDTO = new TransactionEntryDTO("1", 50L, -50.0, TransactionType.DEPOSITO);

        // Act & Assert
        webTestClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionEntryDTO)
                .exchange()
                .expectStatus().isBadRequest(); // Assuming the controller handles the exception and returns 400
    }

    @Test
    void createTransaction_shouldThrowAccountNotFoundException_whenAccountDoesNotExist() {
        // Arrange
        String accountId = "1";
        TransactionEntryDTO transactionEntryDTO = new TransactionEntryDTO(accountId, 100L, 200.0, TransactionType.DEPOSITO);

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.post()
                .uri("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionEntryDTO)
                .exchange()
                .expectStatus().isNotFound(); // Assuming the controller handles the exception and returns 404

        Mockito.verify(accountRepository).findById(accountId);
        Mockito.verifyNoInteractions(transactionRepository);
    }
}