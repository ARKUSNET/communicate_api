package com.droneservice.communicateapi.util;

public class Utils {

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";

    private Utils() {

    }

    public static boolean isEmptyString(String value) {
        return value == null || value.isEmpty();
    }
}
