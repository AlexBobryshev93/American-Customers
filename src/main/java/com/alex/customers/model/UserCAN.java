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

    private Province province;
    private String city;

    // contractions used (taken from Wikipedia)
    public enum Province {
        ON, QC, BC, AB, MB, SK, NS, NB, NL, PE, NT, YT, NU
    }
}