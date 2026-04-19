package com.mishal.project.HMS.strategy;

import com.mishal.project.HMS.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        boolean isTodayHoliday = true; // TODO - add array of holidays
        if(isTodayHoliday) price = price.multiply(BigDecimal.valueOf(1.25));
        return price;
    }
}
