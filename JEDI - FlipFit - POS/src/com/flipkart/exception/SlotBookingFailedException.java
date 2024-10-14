package com.flipkart.exception;



/**
 * Custom exception class for handling slot booking failure scenarios.
 * This exception is thrown when a slot booking fails.
 *

 */
public class SlotBookingFailedException extends Exception {

    /**
     * Overrides the getMessage() method from the Exception class to provide
     * a custom error message when the slot booking fails.
     *
     * @return String Custom error message with color formatting.

     */
    @Override
    public String getMessage() {
        // Returns a custom error message with color formatting when this exception is thrown
        return   "Unable to book slot. Please try again"  ;
    }
}

