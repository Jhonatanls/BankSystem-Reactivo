package com.training.reactive_bank_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "accounts")
public class Account {

    @Id
    private String accountId;
    private Long accountNumber;
    private Double balance = 0.0;
    private Long userId;

}

