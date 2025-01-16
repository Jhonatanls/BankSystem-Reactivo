package com.training.reactive_bank_system.dto;

import com.training.reactive_bank_system.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEntryDTO {

    private String accountId;
    private Long userId;
    private Double amount;
    private TransactionType transactionType;

}
