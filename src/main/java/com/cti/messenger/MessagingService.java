package com.cti.messenger;

import com.cti.common.exception.UserNotFoundException;
import com.cti.service.UserService;
import com.cti.smtp.Mailer;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 */
public class MessagingService {
    @Inject
    private MessageRepository messageRepository;
    @Inject
    private ConversationRepository conversationRepository;
    @Inject
    private UserService userService;
    @Inject
    private Mailer mailer;

    public void send(Message message) throws UserNotFoundException {
        if(userService.isUsernameRegistered(message.getSender()) &&
                userService.isUsernameRegistered(message.getReceipient())) {
            // if there is an on-going conversation between the buyer and seller already,
            // use the existing one, otherwise create a new one
            Optional<Conversation> result =
                    conversationRepository.getConversationBetween(message.getSender(), message.getReceipient());
            if(result.isPresent()) {
                message.setConversationId(result.get().getConversationId());
                messageRepository.addMessage(message);
            } else {
                Conversation conversation = new Conversation();
                conversation.addParticipant(message.getSender());
                conversation.addParticipant(message.getReceipient());
                message.setConversationId(conversation.getConversationId());
                messageRepository.addMessage(message);
                conversationRepository.addConversation(conversation);
            }

            // TODO: alert user of new message
        } else {
            throw new UserNotFoundException(MessageFormat.format("Either {0} and/or {1} are not registered users",
                                                                message.getSender(),
                                                                message.getReceipient()));
        }
    }

    /**
     * Retrieves all messages sent to user with {@code username}
     * @param username user's username
     */
    public List<Message> getInbox(String username) {
        return messageRepository.getAllMessages(username);
    }

    /**
     * Retrieves the number of messages specified by {@code size} sent after {@code timestamp} to user
     * with {@code username}
     * @param username user's username
     */
    public List<Message> getInbox(String username, Date timestamp, int size) {
        return null;
    }

    /**
     * Retrieves all messages between the seller and buyer
     * @param seller user selling a book
     * @param buyer user interested in buying a book
     */
    public List<Message> getConversation(String seller, String buyer) {
        return null;
    }

    /**
     * Retrieves the number of messages specified by {@code size} after the given {@code timestamp}
     * between the buyer and the seller.
     * @param seller user selling a book
     * @param buyer user interested in buying a book
     * @param timestamp the messages should be on or after the timestamp
     * @param size the number of messages to retrieve
     */
    public List<Message> getConversation(String seller, String buyer, Date timestamp, int size) {
        return null;
    }
}
