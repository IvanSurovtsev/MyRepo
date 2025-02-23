package com.ivansurovtsev.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    //тут EAGER чтобы выгружать всю инфу, а не LAZY для выгружения ее части.
    private Collection<CustomerRole> roles;

}
