package com.alex.customers.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "states_us")
@Data
@NoArgsConstructor
public class StateUS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<StateUS> neighbors = new ArrayList<>();

    public StateUS(String name) {
        this.name = name;
    }

    public void addNeighbor(StateUS neighbor) {
        if (neighbors.contains(neighbor) && neighbor.getNeighbors().contains(this)) return;
        neighbors.add(neighbor);
        neighbor.getNeighbors().add(this);
    }

    public boolean isNeighbor(StateUS state) {
        return neighbors.contains(state) ? true : false;
    }

    @Override
    public String toString() {
        return "StateUS{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
