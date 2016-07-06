package com.cti.messenger;

import java.util.Optional;

/**
 * @author ifeify
 */
public interface ConversationRepository {
    boolean hasConversationBetween(String sender, String receipient);

    Optional<Conversation> getConversation(String conversationId);

    void addConversation(Conversation conversation);

    Optional<Conversation> getConversationBetween(String sender, String receipient);
}
