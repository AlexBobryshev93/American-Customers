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

    // used for update
    public UserCAN(User user) {
        if (user instanceof UserUS) this.id = ((UserUS) user).getId();
        else if (user instanceof UserCAN) this.id = ((UserCAN) user).getId();

        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setCountry(Country.CANADA);
    }

    // contractions used (taken from Wikipedia)
    public enum Province {
        ON, QC, BC, AB, MB, SK, NS, NB, NL, PE, NT, YT, NU
    }
}
