package com.example.currencymicroservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client extends BaseEntity{

    private String name;
    private String surname;
    private int account;
    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<MonthLimitByService> limitForService;
    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<MonthLimitByGoods> limitForGoods;
}
