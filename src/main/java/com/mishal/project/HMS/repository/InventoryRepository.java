package com.mishal.project.HMS.repository;

import com.mishal.project.HMS.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
