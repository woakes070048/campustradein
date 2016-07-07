package com.cti.messenger;

import java.util.Optional;

/**
 * @author ifeify
 */
public interface ConversationRepository {

    Optional<Conversation> getConversation(String conversationId);

    Optional<Conversation> getConversationBetween(String buyer, String seller);

    void addConversation(Conversation conversation);


}
