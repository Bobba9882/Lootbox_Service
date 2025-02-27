package com.studentgacha.lootbox.repository;

import com.studentgacha.lootbox.model.Lootbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LootboxRepository extends JpaRepository<Lootbox, Long> {
}
