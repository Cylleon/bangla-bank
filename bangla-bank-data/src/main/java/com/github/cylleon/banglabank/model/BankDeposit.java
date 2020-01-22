package com.github.cylleon.banglabank.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

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
    private Double amount;


    @Tolerate
    public BankDeposit() {
    }
}
