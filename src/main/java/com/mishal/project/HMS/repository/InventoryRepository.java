package com.mishal.project.HMS.repository;

import com.mishal.project.HMS.entity.Inventory;
import com.mishal.project.HMS.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByDateAfterAndRoom(LocalDate dateAfter, Room room);
}
