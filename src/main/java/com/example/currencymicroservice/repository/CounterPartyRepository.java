package com.example.currencymicroservice.repository;

import com.example.currencymicroservice.model.Counterparty;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CounterPartyRepository extends JpaRepository<Counterparty, Long> {
    Counterparty getCounterpartyByBankAccount(int account);
}
