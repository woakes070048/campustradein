package com.cti;

/**
 * Created by ifeify on 4/28/16.
 */
public enum PayloadType {
    JSON("application/json"), XML("text/xml"), HTML("text/html");

    private String text;

    private PayloadType(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
