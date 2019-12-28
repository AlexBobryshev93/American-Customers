package com.alex.customers.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users_usa")
@Data
public class UserUS extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id")
    private StateUS state;

    public UserUS() {
        this.setCountry(Country.USA);
    }
}
