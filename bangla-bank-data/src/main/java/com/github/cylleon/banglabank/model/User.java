package com.github.cylleon.banglabank.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@EqualsAndHashCode
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    private String password;

    @Setter
    private Double balance;

    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
          name = "user_authority",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "authority_id")}
    )
    private Set<Authority> authorities;
    
    @Setter
    @OneToMany
    private List<Transaction> sentTransactions;
    
    @Setter
    @OneToMany
    private List<Transaction> receivedTransactions;
    

    public User() {
        this.authorities = new HashSet<>();
        this.sentTransactions = new ArrayList<>();
        this.receivedTransactions = new ArrayList<>();
    }
}
