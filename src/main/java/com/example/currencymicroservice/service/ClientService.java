package com.example.currencymicroservice.service;

import com.example.currencymicroservice.model.Client;
import com.example.currencymicroservice.model.MonthLimitByGoods;
import com.example.currencymicroservice.model.MonthLimitByService;
import com.example.currencymicroservice.repository.ClientRepository;
import com.example.currencymicroservice.repository.CurrencyRepository;
import com.example.currencymicroservice.repository.MonthLimitByGoodsRepository;
import com.example.currencymicroservice.repository.MonthLimitByServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final CurrencyRepository currencyRepository;
    @Lazy
    @Autowired
    private MonthLimitByGoodsService monthLimitByGoodsService;
    @Lazy
    @Autowired
    private MonthLimitByServiceService monthLimitByServiceService;
    private final MonthLimitByServiceRepository mLSRepository;
    private final MonthLimitByGoodsRepository mLGRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    public Client getClient(Long id) {
        System.out.println(clientRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Client does not exist")));
        return clientRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Client does not exist"));
    }

    public Client getClientByAccount(int account) {
        if(clientRepository.getClientByAccount(account) == null) {
            throw new EntityNotFoundException("transaction failed(wrong client bank account number)");
        }
    return clientRepository.getClientByAccount(account);
    }

    public void createClient(String name, String surname, int account) {

        if (clientRepository.getClientByAccount(account) == null) {

            Client client = buildClient(name, surname, account);

            clientRepository.save(client);

            MonthLimitByService monthLimitByService = monthLimitByServiceService.getLastMLSLimit(getClientByAccount(account).getId());
            MonthLimitByGoods monthLimitByGoods = monthLimitByGoodsService.getLastMLGLimit(getClientByAccount(account).getId());
            monthLimitByGoods.setClient(client);
            monthLimitByService.setClient(client);
            mLSRepository.save(monthLimitByService);
            mLGRepository.save(monthLimitByGoods);

        } else {
            throw new IllegalArgumentException("account already exists");
        }
    }
    public void updateClientLimit(Long id, String category, Double limit) {
        Client client = getClient(id);
        if(category.equals("service")) {
            List<MonthLimitByService> mls = client.getLimitForService();
            mls.add(monthLimitByServiceService.updateMLS(id, limit));
            client.setLimitForService(mls);
        } else {
            List<MonthLimitByGoods> mls = client.getLimitForGoods();
            mls.add(monthLimitByGoodsService.updateMLG(id, limit));
            client.setLimitForGoods(mls);
        }
        clientRepository.save(client);
    }
    /*public void updateClient(Long id, String name, String surname, int account, Double limitService, Double limitGoods) {
        Client client = getClient(id);
        client.setName(name);
        client.setSurname(surname);
        if(getClientByAccount(account) != null) {
            throw new IllegalArgumentException("account number already exists");
        }
        client.setAccount(account);
        client.setLimitForService(updateLimitForService(id, limitService));
        client.setLimitForGoods(updateLimitForGoods(id, limitGoods));
        clientRepository.save(client);
    }*/
    public void deleteClient(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Client buildClient(String name, String surname, int account) {
        return Client.builder()
                .name(name)
                .surname(surname)
                .account(account)
                .limitForService(monthLimitByServiceService.createMLS(1000.00))
                .limitForGoods(monthLimitByGoodsService.createMLG(1000.00))
                .build();
    }
}
