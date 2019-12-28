package com.alex.customers.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users_canada")
@Data
public class UserCAN extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Province province;
    private String city;

    public UserCAN() {
        this.setCountry(Country.CANADA);
    }

    // contractions used (taken from Wikipedia)
    public enum Province {
        ON, QC, BC, AB, MB, SK, NS, NB, NL, PE, NT, YT, NU
    }
}
