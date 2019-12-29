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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "state_id")
    private StateUS state;

    @Transient
    private String stateName; // for registration form only

    public UserUS() {
        this.setCountry(Country.USA);
    }

    // used for update
    public UserUS(User user) {
        if (user instanceof UserUS) this.id = ((UserUS) user).getId();
        else if (user instanceof UserCAN) this.id = ((UserCAN) user).getId();

        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setCountry(Country.USA);
    }
}
