package com.cti.messenger;

import com.cti.App;
import com.cti.config.AppConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 */
public class MessageRepositoryImpl implements MessageRepository {
    private MongoCollection<Document> messageCollection;

    @Inject
    public MessageRepositoryImpl(MongoClient mongoClient) {
        messageCollection = mongoClient.getDatabase(AppConfig.DATABASE).getCollection("messages");
        messageCollection.createIndex(Indexes.compoundIndex(Indexes.ascending("receipient"),
                                                            Indexes.descending("timestamp")));
        messageCollection.createIndex(Indexes.ascending("conversationId"));
    }

    @Override
    public void addMessage(Message message) {
        Document document = new Document("conversationId", message.getConversationId())
                                    .append("sender", message.getSender())
                                    .append("receipient", message.getReceipient())
                                    .append("subject", message.getSubject())
                                    .append("timestamp", message.getTimestamp())
                                    .append("read", message.isRead())
                                    .append("replied", message.replied())
                                    .append("body", message.getBody());
        messageCollection.insertOne(document);
    }

    @Override
    public List<Message> getAllMessages(String username) {
        List<Document> documents = new ArrayList<>();
        messageCollection.find(Filters.eq("receipient", username))
                            .sort(Sorts.descending("timestamp"))
                            .into(documents);
        List<Message> messages = new ArrayList<>();
        mapToDomain(documents, messages);
        return messages;
    }

    @Override
    public Optional<Message> getMessage(String messageId) {
        Document document = messageCollection.find(Filters.eq("messageId", messageId)).first();
        if(document == null) {
            return Optional.empty();
        }
        Message message = new Message();
        mapToDomain(document, message);
        return Optional.of(message);
    }

    private void mapToDomain(List<Document> documents, List<Message> messages) {
        for(Document document : documents) {
            if(document != null) {
                Message message = new Message();
                message.setTimestamp(document.getLong("timestamp"));
                message.setSender(document.getString("sender"));
                message.setReceipient(document.getString("receipient"));
                message.setSubject(document.getString("subject"));
                message.setBody(document.getString("body"));
                message.setConversationId(document.getString("conversationId"));
                message.setRead(document.getBoolean("read"));
                message.setReplied(document.getBoolean("replied"));
                messages.add(message);
            }
        }
    }

    private void mapToDomain(Document document, Message message) {
        if(document != null) {
            message.setTimestamp(document.getLong("timestamp"));
            message.setSender(document.getString("sender"));
            message.setReceipient(document.getString("receipient"));
            message.setSubject(document.getString("subject"));
            message.setBody(document.getString("body"));
            message.setConversationId(document.getString("conversationId"));
            message.setRead(document.getBoolean("read"));
            message.setReplied(document.getBoolean("replied"));
        }

    }
}
