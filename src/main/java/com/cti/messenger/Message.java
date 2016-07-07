package com.cti.messenger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ifeify
 */
public class Message {
    private String conversationId;
    private String sender;
    private String receipient;
    private String subject;
    private boolean read = false;
    private boolean replied = false;
    private long timestamp = System.currentTimeMillis();
    private String body;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceipient() {
        return receipient;
    }

    public void setReceipient(String receipient) {
        this.receipient = receipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public boolean replied() {
        return replied;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sender", sender)
                .add("conversationId", conversationId)
                .add("receipient", receipient)
                .add("subject", subject)
                .add("read", read)
                .add("replied", replied)
                .add("timestamp", timestamp)
                .add("body", body)
                .toString();
    }
}
