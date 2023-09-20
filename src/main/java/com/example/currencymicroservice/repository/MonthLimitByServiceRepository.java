package com.example.currencymicroservice.repository;

import com.example.currencymicroservice.model.MonthLimitByService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MonthLimitByServiceRepository extends JpaRepository<MonthLimitByService, Long> {
    List<MonthLimitByService> findAll();
}
