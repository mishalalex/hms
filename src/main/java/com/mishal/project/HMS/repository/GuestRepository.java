package com.mishal.project.HMS.repository;

import com.mishal.project.HMS.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
