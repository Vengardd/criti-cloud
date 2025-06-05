package com.krajust.criti_cloud_back.common;

import java.util.Optional;

public class HelpingMethods {

    public static <T> T checkRequired(T object, String param) {
        if (object == null) {
            throw new IllegalArgumentException("Entity: " + param + " is required, but is null.");
        }
        return object;
    }

    public static <T> Optional<T> some(T object) {
        return Optional.ofNullable(object);
    }
}
