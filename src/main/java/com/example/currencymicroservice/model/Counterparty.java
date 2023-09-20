package com.example.currencymicroservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Counterparty extends BaseEntity{

    private String name;
    private int bankAccount;
}
