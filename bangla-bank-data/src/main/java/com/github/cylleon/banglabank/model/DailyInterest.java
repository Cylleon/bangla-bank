package com.github.cylleon.banglabank.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Builder
@EqualsAndHashCode
@Entity(name = "daily_interest")
public class DailyInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE,
                                                  CascadeType.REFRESH, CascadeType.DETACH})
    private BankDeposit bankDeposit;

    @Setter
    private Double interest;

    @Setter
    private Instant timestamp;

    @Tolerate
    public DailyInterest() {
    }
}
