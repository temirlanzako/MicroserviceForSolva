package com.example.currencymicroservice.repository;

import com.example.currencymicroservice.model.Currency;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByName(String name);
}
