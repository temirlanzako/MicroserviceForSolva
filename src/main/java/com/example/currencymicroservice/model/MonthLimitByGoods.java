package com.example.currencymicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthLimitByGoods extends BaseEntity {

    private Double limitSum;
    private Double balance;
    private LocalDateTime limitDatetime;
    @ManyToOne
    private Currency currencyShortName;
    @ManyToOne
    @JsonIgnore
    private Client client;
}
