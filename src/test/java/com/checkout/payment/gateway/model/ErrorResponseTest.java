package com.checkout.payment.gateway.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @DisplayName("ErrorResponse constructor 1")
    @Test
    void t1() {
        //given
        var errorResponse = new ErrorResponse(500, "Message 1");
        //then
        assertEquals("Message 1", errorResponse.getMessage());
        assertEquals(500, errorResponse.getStatus());
    }

    @DisplayName("ErrorResponse constructor 2")
    @Test
    void t2() {
        //given
        var errorResponse = new ErrorResponse(500, "Message 1", "Details message 1");
        //then
        assertEquals("Message 1", errorResponse.getMessage());
        assertEquals(500, errorResponse.getStatus());
        assertEquals("Details message 1", errorResponse.getDetails());
    }

}