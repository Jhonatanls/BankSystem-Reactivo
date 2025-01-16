package com.training.reactive_bank_system.dto;

import com.training.reactive_bank_system.model.Account;
import com.training.reactive_bank_system.model.Transaction;
import com.training.reactive_bank_system.model.TransactionType;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionMapper {

    public Transaction DTOtoEntity(TransactionEntryDTO transactionEntryDTO, Account account) {
        if (transactionEntryDTO == null) {
            throw new IllegalArgumentException("El DTO de la transacción no puede ser nulo");
        }
        return Transaction.builder()
                .userId(transactionEntryDTO.getUserId())
                .accountId(transactionEntryDTO.getAccountId())
                .amount(transactionEntryDTO.getAmount())
                .transactionType(transactionEntryDTO.getTransactionType())
                .success(true)
                .transactionDate(new Date())
                .currentBalance(account.getBalance())
                .previousBalance(transactionEntryDTO.getTransactionType().equals(TransactionType.DEPOSITO)?
                                account.getBalance()-transactionEntryDTO.getAmount():
                                account.getBalance()+transactionEntryDTO.getAmount())
                .build();
    }

    public TransactionResponseDTO toExitDTO(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("transaction can't be empty");
        }
        return TransactionResponseDTO.builder()
                .transactionId(transaction.getTransactionId())
                .userId(transaction.getUserId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .success(transaction.getSuccess())
                .transactionDate(transaction.getTransactionDate())
                .previousBalance(transaction.getPreviousBalance())
                .currentBalance(transaction.getCurrentBalance())
                .build();
    }


}
