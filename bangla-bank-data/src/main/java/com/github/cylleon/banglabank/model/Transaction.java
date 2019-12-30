package com.github.cylleon.banglabank.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Builder
@EqualsAndHashCode
@Entity(name = "transaction")
public class Transaction implements Serializable {

    @Tolerate
    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE,
                                                  CascadeType.REFRESH, CascadeType.DETACH})
    private User sender;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE,
                                                  CascadeType.REFRESH, CascadeType.DETACH})
    private User recipient;

    @Setter
    private Instant timestamp;

    @Setter
    private Double amount;
}
