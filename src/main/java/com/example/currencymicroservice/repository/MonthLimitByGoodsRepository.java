package com.example.currencymicroservice.repository;

import com.example.currencymicroservice.model.MonthLimitByGoods;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MonthLimitByGoodsRepository extends JpaRepository<MonthLimitByGoods, Long> {
    List<MonthLimitByGoods> findAll();
}
