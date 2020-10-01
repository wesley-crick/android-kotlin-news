package com.wesley.crick.kotlinnews.objects;

import androidx.annotation.Nullable;

import org.json.JSONObject;

/**
 * Generic class to help explain if a call failed or succeeded.
 * Manually set the `obj` with data if needed.
 */
public class ResponseTemplate<T> {
    private int code;       // Return 0 for a success, anything else for an error
    private String message; // Describe the data returned or the error message
    @Nullable
    public T obj = null;    // Data for the returned object

    /**
     *
     * @param code 0 for a success, else error
     * @param message Describe the data returned or the error message
     */
    public ResponseTemplate(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     *
     * @return 0 for a success, else error
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @return Describe the data returned or the error message
     */
    public String getMessage() {
        return message;
    }
}
