package com.mishal.project.HMS.strategy;

import com.mishal.project.HMS.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
