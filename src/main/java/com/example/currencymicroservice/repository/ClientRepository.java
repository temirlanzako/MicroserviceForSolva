package com.example.currencymicroservice.repository;

import com.example.currencymicroservice.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client getClientByAccount(int account);
}
