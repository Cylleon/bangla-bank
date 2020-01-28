package com.github.cylleon.banglabank.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@Entity(name = "bank_deposit")
public class BankDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE,
                                                 CascadeType.REFRESH, CascadeType.DETACH})
    private User user;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE,
                                                  CascadeType.REFRESH, CascadeType.DETACH})
    private List<DailyInterest> dailyInterests;

    @Setter
    private Double amount;


    @Tolerate
    public BankDeposit() {
    }
}
