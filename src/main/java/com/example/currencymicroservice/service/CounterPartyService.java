package com.example.currencymicroservice.service;

import com.example.currencymicroservice.model.Client;
import com.example.currencymicroservice.model.Counterparty;
import com.example.currencymicroservice.repository.CounterPartyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CounterPartyService {

    private final CounterPartyRepository counterPartyRepository;

    public List<Counterparty> getAllCounterParties() {
        return counterPartyRepository.findAll();
    }
    public Counterparty getCounterParty(Long id) {
        return counterPartyRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("CounterParty not found"));
    }
    public Counterparty getCounterPartyByAccount(int account) {
        if(counterPartyRepository.getCounterpartyByBankAccount(account) == null) {
            throw new EntityNotFoundException("transaction failed(wrong counterParty account number)");
        }
        return counterPartyRepository.getCounterpartyByBankAccount(account);
    }
}
