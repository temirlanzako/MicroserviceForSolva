package com.example.currencymicroservice.api;

import com.example.currencymicroservice.model.Transaction;
import com.example.currencymicroservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(value = "/get_all")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
    @GetMapping(value = "/get_all/exceeded")
    public List<Transaction> getAllTransactionsExceeded() {
        return transactionService.getAllTransactionsExceededLimit();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createTransaction(@RequestParam(name ="account_from") int accountFrom,
                                               @RequestParam(name ="account_to") int accountTo,
                                               @RequestParam(name ="sum") Double sum,
                                               @RequestParam(name ="category") String category) {
        try {
            transactionService.createTransaction(accountFrom, accountTo, sum, category);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
