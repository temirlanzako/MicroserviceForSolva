package com.example.currencymicroservice.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Currency extends BaseEntity{

    private String name;
//    USD(0),
//    RUB(1),
//    KZT(2);
//    private final int intValue;
//
//    Currency (int intValue) {
//        this.intValue = intValue;
//    }
//    public int getIntValue() {
//        return this.intValue;
//    }
}
