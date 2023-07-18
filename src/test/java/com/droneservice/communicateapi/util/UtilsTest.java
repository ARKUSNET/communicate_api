package com.droneservice.communicateapi.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void isEmptyString() {
        assertFalse(Utils.isEmptyString("asf"));
        assertTrue(Utils.isEmptyString(""));
    }
}