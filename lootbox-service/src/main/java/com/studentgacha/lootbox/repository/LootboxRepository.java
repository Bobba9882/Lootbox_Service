package com.studentgacha.lootbox.repository;

import com.studentgacha.lootbox.model.Lootbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LootboxRepository extends JpaRepository<Lootbox, Long> {
    Optional<Lootbox> findByName(String name);
}
