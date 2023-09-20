package com.example.currencymicroservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class CurrencyRate extends BaseEntity{
    private Date date;
    @Column(name = "KZT")
    private double kzt;
    @Column(name = "RUB")
    private double rub;
}
