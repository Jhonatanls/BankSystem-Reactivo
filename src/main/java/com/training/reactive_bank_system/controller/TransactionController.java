package com.training.reactive_bank_system.controller;

import com.training.reactive_bank_system.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

}
