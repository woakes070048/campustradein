package com.cti.smtp;

import com.google.inject.name.Named;

import java.util.List;
import java.util.Map;

/**
 * @author ifeify
 * Note that all emails are implicitly HTML formatted
 */
public class Email {
    private String from; // by default, all emails to user is from campustradein.com
    private String to;
    private String subject;

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

	public void setFrom(String from) {
		this.from = from;
		
	}
}
