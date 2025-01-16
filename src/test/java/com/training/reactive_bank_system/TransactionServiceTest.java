package com.training.reactive_bank_system;

import com.training.reactive_bank_system.dto.BalanceResponseDTO;
import com.training.reactive_bank_system.dto.TransactionEntryDTO;
import com.training.reactive_bank_system.dto.TransactionMapper;
import com.training.reactive_bank_system.exceptions.AccountNotFoundException;
import com.training.reactive_bank_system.exceptions.InvalidTransactionException;
import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.model.Transaction;
import com.training.reactive_bank_system.model.TransactionType;
import com.training.reactive_bank_system.repository.AccountRepository;
import com.training.reactive_bank_system.repository.TransactionRepository;
import com.training.reactive_bank_system.service.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Test
    void createTransaction_shouldReturnBalanceResponseDTO_onValidTransaction() {
        // Arrange
        String accountId = "1";
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(123456789L);
        account.setUserId(1010115618L);
        TransactionEntryDTO transactionEntryDTO = new TransactionEntryDTO(accountId, 100L, 200.0, TransactionType.DEPOSITO);
        Transaction transaction = new Transaction();

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Mono.just(account));
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));
        Mockito.when(transactionMapper.DTOtoEntity(transactionEntryDTO, account)).thenReturn(transaction);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        // Act
        Mono<BalanceResponseDTO> result = transactionService.createTransaction(transactionEntryDTO);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getAccountId().equals(accountId) &&
                        response.getBalance() == 300.0)
                .verifyComplete();

        Mockito.verify(accountRepository).findById(accountId);
        Mockito.verify(accountRepository).save(any(Account.class));
        Mockito.verify(transactionRepository).save(transaction);
    }


    @Test
    void createTransaction_shouldThrowInvalidTransactionException_onNegativeAmount() {
        // Arrange
        TransactionEntryDTO transactionEntryDTO = new TransactionEntryDTO("1",50L, -50.0, TransactionType.DEPOSITO); // Corrected constructor and negative amount

        // Act
        Mono<BalanceResponseDTO> result = transactionService.createTransaction(transactionEntryDTO);

        // Assert
        StepVerifier.create(result)
                .expectError(InvalidTransactionException.class)
                .verify();

    }

    @Test
    void createTransaction_shouldThrowAccountNotFoundException_whenAccountDoesNotExist() {
        // Arrange
        String accountId = "1";
        TransactionEntryDTO transactionEntryDTO = new TransactionEntryDTO(accountId, 100L, 200.0, TransactionType.DEPOSITO);

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Mono.empty());

        // Act
        Mono<BalanceResponseDTO> result = transactionService.createTransaction(transactionEntryDTO);

        // Assert
        StepVerifier.create(result)
                .expectError(AccountNotFoundException.class)
                .verify();

        Mockito.verify(accountRepository).findById(accountId);
        Mockito.verifyNoInteractions(transactionRepository);
    }
}