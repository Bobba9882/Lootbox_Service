package com.studentgacha.lootbox.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lootboxes")
@Getter
@Setter
public class Lootbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price; // Stufi cost

    @OneToMany(mappedBy = "lootbox", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    // Constructors
    public Lootbox() {}
    public Lootbox(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
