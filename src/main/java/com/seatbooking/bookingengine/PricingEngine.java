package com.seatbooking.bookingengine;

import com.seatbooking.entity.Seat;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PricingEngine {

    /**
     * Calculates total booking amount.
     *
     * Current Rule:
     * Sum of all selected seat prices.
     *
     * Future:
     * - Coupons
     * - GST
     * - Festival Discount
     * - Membership Discount
     * - Dynamic Pricing
     * - Weekend Pricing
     */
    public BigDecimal calculateTotalAmount(
            List<Seat> seats) {

        BigDecimal total =
                BigDecimal.ZERO;

        for (Seat seat : seats) {

            total = total.add(

                    BigDecimal.valueOf(

                            seat.getZone().getPrice()
                    )
            );
        }

        return total;
    }
}