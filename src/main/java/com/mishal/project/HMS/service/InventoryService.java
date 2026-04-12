package com.mishal.project.HMS.service;

import com.mishal.project.HMS.entity.Room;

public interface InventoryService {
    void initializeRoomForOneYear(Room room);
    void deleteFutureInventories(Room room);
}
