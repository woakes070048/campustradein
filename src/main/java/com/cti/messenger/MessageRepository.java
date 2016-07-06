package com.cti.messenger;

import java.util.List;

/**
 * @author ifeify
 */
public interface MessageRepository {
    void addMessage(Message message);

    List<Message> getAllMessages(String username);
}
