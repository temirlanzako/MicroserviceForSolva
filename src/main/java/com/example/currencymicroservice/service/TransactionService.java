package com.example.currencymicroservice.service;
import com.example.currencymicroservice.model.Client;
import com.example.currencymicroservice.model.Transaction;
import com.example.currencymicroservice.repository.CurrencyRepository;
import com.example.currencymicroservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClientService clientService;
    private final CurrencyRepository currencyRepository;
    private final CounterPartyService counterPartyService;
    private final MonthLimitByServiceService monthLimitByServiceService;
    private final MonthLimitByGoodsService monthLimitByGoodsService;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllTransactionsExceededLimit() {
        return transactionRepository.findAllByIsExceededTrue();
    }

    public void createTransaction(int accountFrom, int accountTo, Double sum, String category) {

        Client client = clientService.getClientByAccount(accountFrom);

        Transaction transaction = buildTransaction(client.getAccount(),
                counterPartyService.getCounterPartyByAccount(accountTo).getBankAccount(),
                sum, category, client);

        List<Transaction> transactions = getClientCategoryTransactions(client.getId(), category);
        transactions.add(transaction);

        Double balance = getCategoryBalance(client.getId(),sum, category);


        for(Transaction tr : transactions) {
            balance -= tr.getSum();
        }

        if(balance < 0) {
            transaction.setIsExceeded(true);
        } else {
            transaction.setIsExceeded(false);
        }

        transactionRepository.save(transaction);
    }
    private List<Transaction> getClientCategoryTransactions(Long clientId, String expenseCategory) {
        List<Transaction> transactions = transactionRepository.findAllByExpenseCategory(expenseCategory);
        List<Transaction> clientTransactions = new ArrayList<>();
        for (Transaction tr : transactions) {
            if (tr.getAccountFrom() == clientService.getClient(clientId).getAccount()) {
                clientTransactions.add(tr);
            }
        }
        return clientTransactions;
    }

    private Transaction buildTransaction(int accountFrom, int accountTo, Double sum, String category, Client client) {
        return Transaction.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .currencyShortName(currencyRepository.findByName("USD"))
                .sum(sum)
                .expenseCategory(category)
                .datetime(LocalDateTime.now())
                .client(client)
                .build();
    }
    private Double getCategoryBalance(Long clientId, Double sum, String category) {
        Double balance;
        if(category.equals("service")) {
            balance = monthLimitByServiceService.getBalanceForService(clientId);
            monthLimitByServiceService.updateBalanceForMLS(clientId, sum);
        } else {
            balance = monthLimitByGoodsService.getBalanceForGoods(clientId);
            monthLimitByGoodsService.updateBalanceForMLG(clientId, sum);
        }
        return balance;
    }
}
