package com.training.reactive_bank_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "account_balances")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalance {

    @Id
    private ObjectId accountId;
    private Double currentBalance;

}
