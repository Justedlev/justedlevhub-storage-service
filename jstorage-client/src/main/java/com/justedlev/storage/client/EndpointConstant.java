package com.justedlev.storage.client;

public final class EndpointConstant {
    public static final String V1 = "/v1";
    public static final String UPLOAD = "/upload";
    public static final String DELETE = "/delete";

    // File
    public static final String FILE = V1 + "/file";
    public static final String FILE_NAME = "/" + PathVariableConstant.FILE_NAME;
    public static final String FILE_NAME_DELETE = FILE_NAME + DELETE;
    public static final String FILE_FILE_NAME = FILE + FILE_NAME;
    public static final String FILE_FILE_NAME_DELETE = FILE + FILE_NAME_DELETE;
    public static final String FILE_UPLOAD = FILE + UPLOAD;

    private EndpointConstant() {
        throw new IllegalStateException("Constants class");
    }
}
