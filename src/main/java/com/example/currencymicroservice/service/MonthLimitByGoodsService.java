package com.example.currencymicroservice.service;

import com.example.currencymicroservice.model.Client;
import com.example.currencymicroservice.model.MonthLimitByGoods;
import com.example.currencymicroservice.repository.CurrencyRepository;
import com.example.currencymicroservice.repository.MonthLimitByGoodsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MonthLimitByGoodsService {

    private final MonthLimitByGoodsRepository monthLimitByGoodsRepository;
    private final ClientService clientService;
    private final CurrencyRepository currencyRepository;

    public List<MonthLimitByGoods> getClientsMLsGLimit(Long clientId) {
        return clientService.getClient(clientId).getLimitForGoods();
    }

    public MonthLimitByGoods getLastMLGLimit(Long id) {
        List<MonthLimitByGoods> monthLimitByGoodsList = getClientsMLsGLimit(id);
        return monthLimitByGoodsList.get(monthLimitByGoodsList.size() - 1);
    }

    public Double getBalanceForGoods(Long id) {
        Client client = clientService.getClient(id);
        List<MonthLimitByGoods> mls = new ArrayList<>();
        for (MonthLimitByGoods c : monthLimitByGoodsRepository.findAll()) {
            if (c.getClient().getId().equals(client.getId())) {
                mls.add(c);
            }
        }
        return mls.get(mls.size() - 1).getBalance();
    }

    public List<MonthLimitByGoods> createMLG(Double limit) {
        List<MonthLimitByGoods> MLGList = new ArrayList<>();
        MLGList.add(monthLimitByGoodsRepository.save(MonthLimitByGoods.builder()
                .limitSum(limit)
                .balance(limit)
                .currencyShortName(currencyRepository.findByName("USD"))
                .limitDatetime(LocalDateTime.now()).build()));
        return MLGList;
    }

    public void updateBalanceForMLG(Long id, Double sum) {
        Double balance = getBalanceForGoods(id) - sum;
        monthLimitByGoodsRepository.save(MonthLimitByGoods.builder()
                .client(clientService.getClient(id))
                .balance(balance)
                .currencyShortName(currencyRepository.findByName("USD"))
                .limitDatetime(LocalDateTime.now()).build());
    }

    public MonthLimitByGoods updateMLG(Long id, Double limit) {
        MonthLimitByGoods mls = MonthLimitByGoods.builder()
                .client(clientService.getClient(id))
                .currencyShortName(currencyRepository.findByName("USD"))
                .limitDatetime(LocalDateTime.now())
                .balance(limit - (getLastMLGLimit(id).getLimitSum() - getBalanceForGoods(id)))
                .limitSum(limit)
                .build();
        monthLimitByGoodsRepository.save(mls);
        return mls;
    }
}
