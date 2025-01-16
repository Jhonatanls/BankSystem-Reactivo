package com.training.reactive_bank_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String transactionId;
    private String accountId;
    private Long userId;
    private TransactionType transactionType;
    private Double amount;
    private LocalDateTime transactionDate;
    private Double previousBalance;
    private Double currentBalance;
    private boolean success;

}
