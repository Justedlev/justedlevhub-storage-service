package com.justedlev.service.storage.utils;

import org.apache.commons.lang3.RandomStringUtils;

public final class Generator {
    private Generator() {
        throw new IllegalStateException("Util class");
    }

    public static String generateFileName() {
        return RandomStringUtils.randomAlphanumeric(32, 64);
    }
}
