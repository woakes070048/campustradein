package com.cti.smtp;

import java.util.List;
import java.util.Map;

/**
 * Created by ifeify on 4/30/16.
 */
public class Email {
    private String subject;
    private String to;
    private String from;
    private List<String> cc;
    private List<String> bcc;
    private Map<String, String> attachments;
    private String body;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
