package com.justedlev.service.storage.constant;

public final class EndpointConstant {
    public static final String V1 = "/v1";
    public static final String UPLOAD = "/upload";

    // File
    public static final String FILE = V1 + "/file";
    public static final String FILE_ID = "/" + PathVariableConstant.FILE_ID;

    private EndpointConstant() {
        throw new IllegalStateException("Constants class");
    }
}
