package com.justedlev.service.storage.constant;

public final class EndpointConstant {
    public static final String V1 = "/v1";
    public static final String UPLOAD = "/upload";
    public static final String DOWNLOAD = "/download";
    public static final String PREVIEW = "/preview";

    // File
    public static final String FILE = V1 + "/file";
    public static final String FILE_ID = "/" + PathVariableConstant.FILE_ID;
    public static final String PREVIEW_FILE_ID = PREVIEW + FILE_ID;
    public static final String DOWNLOAD_FILE_ID = DOWNLOAD + FILE_ID;

    private EndpointConstant() {
        throw new IllegalStateException("Constants class");
    }
}
