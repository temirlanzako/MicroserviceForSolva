package com.example.currencymicroservice.service;

import com.example.currencymicroservice.model.Client;
import com.example.currencymicroservice.model.MonthLimitByService;
import com.example.currencymicroservice.repository.CurrencyRepository;
import com.example.currencymicroservice.repository.MonthLimitByServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MonthLimitByServiceService {

    private final MonthLimitByServiceRepository monthLimitByServiceRepository;
    private final CurrencyRepository currencyRepository;
    private final ClientService clientService;

    public List<MonthLimitByService> getClientsMLsGLimit(Long clientId) {
        return clientService.getClient(clientId).getLimitForService();
    }
    public MonthLimitByService getLastMLSLimit(Long id) {
        List<MonthLimitByService> MLSList = getClientsMLsGLimit(id);
        return MLSList.get(MLSList.size()-1);
    }
    public Double getBalanceForService(Long id) {
        Client client = clientService.getClient(id);
        List<MonthLimitByService> mls = new ArrayList<>();
        for(MonthLimitByService c : monthLimitByServiceRepository.findAll()) {
            if(c.getClient().getId().equals(client.getId())) {
                mls.add(c);
            }
        }
        return mls.get(mls.size()-1).getBalance();
    }
    public List<MonthLimitByService> createMLS(Double limit) {
        List<MonthLimitByService> MLSList = new ArrayList<>();
        MLSList.add(monthLimitByServiceRepository.save(MonthLimitByService.builder()
                .limitSum(limit)
                .balance(limit)
                .currencyShortName(currencyRepository.findByName("USD"))
                .limitDatetime(LocalDateTime.now()).build()));
        return MLSList;
    }
    public void updateBalanceForMLS(Long id, Double sum) {
        Double balance = getBalanceForService(id) - sum;
        monthLimitByServiceRepository.save(MonthLimitByService.builder()
                .client(clientService.getClient(id))
                .balance(balance)
                .currencyShortName(currencyRepository.findByName("USD"))
                .limitDatetime(LocalDateTime.now()).build());
    }
    public MonthLimitByService updateMLS(Long id, Double limit) {
        MonthLimitByService mls = MonthLimitByService.builder()
                .client(clientService.getClient(id))
                .currencyShortName(currencyRepository.findByName("USD"))
                .limitDatetime(LocalDateTime.now())
                .balance(limit - (getLastMLSLimit(id).getLimitSum() - getBalanceForService(id)))
                .limitSum(limit)
                .build();
        monthLimitByServiceRepository.save(mls);
        return mls;
    }
}
