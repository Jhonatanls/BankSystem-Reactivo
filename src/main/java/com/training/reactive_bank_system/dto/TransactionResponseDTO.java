package com.training.reactive_bank_system.dto;

import com.training.reactive_bank_system.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {

    private String transactionId;

    private Long userId;

    private String accountId;

    private Double amount;

    private TransactionType transactionType;

    private Boolean success;

    private Date transactionDate;

    private Double previousBalance;

    private Double currentBalance;


}
