package com.tm.trophiesmementoes.backend.common;

public final class ErrorCode {
    public static final String NOT_FOUND      = "NOT_FOUND";
    public static final String INVALID_PARAMS = "INVALID_PARAMS";
    public static final String UNAUTHORIZED   = "UNAUTHORIZED";
    public static final String FORBIDDEN      = "FORBIDDEN";
    public static final String RATE_LIMITED   = "RATE_LIMITED";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

    private ErrorCode() {}
}
