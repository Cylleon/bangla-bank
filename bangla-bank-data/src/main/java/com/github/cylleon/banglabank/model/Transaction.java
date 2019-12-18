package com.github.cylleon.banglabank.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transaction")
    private User sender;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transaction")
    private User recipient;

    @Setter
    private Instant timestamp;

    @Setter
    private Double value;
}
