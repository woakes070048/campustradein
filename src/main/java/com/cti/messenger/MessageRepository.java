package com.cti.messenger;

import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 */
public interface MessageRepository {
    void addMessage(Message message);

    List<Message> getAllMessages(String username);

    Optional<Message> getMessage(String messageId);
}
