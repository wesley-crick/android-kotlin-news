package com.wesley.crick.kotlinnews.objects;

import androidx.annotation.Nullable;

import org.json.JSONObject;

/**
 * Generic class to help explain if a call failed or succeeded
 */
public class ResponseTemplate<T> {
    private int code;
    private String message;
    @Nullable
    public T obj = null;

    public ResponseTemplate(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
