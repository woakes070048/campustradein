package com.cti.messenger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author ifeify
 */
public class Conversation {
    private String conversationId;
    private List<String> participants = new ArrayList<>(); // technically should be an array of 2

    public Conversation() {
        conversationId = UUID.randomUUID().toString().replace("-", "");
    }

    public Conversation(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void addParticipant(String user) {
        participants.add(user);
    }

    public void removeParticipant(String user) {
        participants.remove(user);
    }

    public void removeAll() {
        participants.clear();
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
