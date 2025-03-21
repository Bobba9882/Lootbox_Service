package com.studentgacha.lootbox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "item_value", nullable = false)
    private int itemValue; // Stufi value when sold

    @Column(name = "dropchance", nullable = false)
    private double dropChance; // Probability (0.0 - 1.0)

    @Column(name="description", nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "lootbox_id")
    private Lootbox lootbox;

    // Constructors
    public Item() {}
    public Item(String name, int itemValue, double dropChance, String description, Lootbox lootbox) {
        this.name = name;
        this.itemValue = itemValue;
        this.dropChance = dropChance;
        this.description = description;
        this.lootbox = lootbox;
    }
}
