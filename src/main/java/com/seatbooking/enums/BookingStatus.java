package com.seatbooking.enums;


/* PAYMENT_PENDING -> User has initiated booking but payment is not completed.
 * CONFIRMED -> Payment completed successfully.
 * FAILED -> Payment failed.
 */
public enum BookingStatus {

    PAYMENT_PENDING,

    CONFIRMED,

    FAILED
}