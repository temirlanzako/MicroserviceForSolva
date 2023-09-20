package com.example.currencymicroservice.repository;

import com.example.currencymicroservice.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByExpenseCategory(String expenseCategory);
    List<Transaction> findAllByIsExceededTrue();
}
