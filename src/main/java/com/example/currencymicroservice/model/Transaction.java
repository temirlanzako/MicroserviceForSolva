package com.example.currencymicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends BaseEntity{

    private int accountFrom;
    private int accountTo;
    @ManyToOne
    private Currency currencyShortName;
    private Double sum;
    private String expenseCategory;
    private LocalDateTime datetime;
    private Boolean isExceeded;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Client client;
}
