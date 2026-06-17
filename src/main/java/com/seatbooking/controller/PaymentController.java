package com.seatbooking.controller;

import com.seatbooking.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Simulate successful payment
     *
     * PAYMENT_PENDING
     * ↓
     * CONFIRMED
     */
    @PostMapping("/{bookingId}/confirm")
    public String confirmPayment(
            @PathVariable Long bookingId) {

        return paymentService.confirmPayment(bookingId);
    }
}