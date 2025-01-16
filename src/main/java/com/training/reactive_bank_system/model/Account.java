package com.training.reactive_bank_system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;

@Data
@Document(collection = "accounts")
public class Account {

    @Id
    private String accountId;
    private Long accountNumber;
    private Double balance = 0.0;

}

