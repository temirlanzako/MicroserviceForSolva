package com.example.currencymicroservice.api;

import com.example.currencymicroservice.model.Client;
import com.example.currencymicroservice.repository.MonthLimitByGoodsRepository;
import com.example.currencymicroservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping(value="/{id}")
    public Client getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createClient(String name,
                                          String surname,
                                          int account) {
        try {
            clientService.createClient(name, surname, account);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id,
                                          @RequestParam String category,
                                          @RequestParam Double limit) {
        try {
            clientService.updateClientLimit(id, category, limit);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /*@PutMapping(value="/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id,
                                          @RequestParam String name,
                                          @RequestParam String surname,
                                          @RequestParam int account,
                                          @RequestParam(name="limit_service") Double limitService,
                                          @RequestParam(name="limit_goods")Double limitGoods) {
        try {
            clientService.updateClient(id, name, surname, account, limitService, limitGoods);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }*/
    /*private String name;
    private String surname;
    private int account;
    @OneToMany
    private List<MonthLimitService> limitForService;
    @OneToMany
    private List<MonthLimitGoods> limitForGoods;*/
}
