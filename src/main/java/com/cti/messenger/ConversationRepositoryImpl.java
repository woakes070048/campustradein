package com.cti.messenger;

import com.cti.config.AppConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 */
public class ConversationRepositoryImpl implements ConversationRepository {
    private MongoCollection<Document> conversationCollection;

    @Inject
    public ConversationRepositoryImpl(MongoClient mongoClient) {
        conversationCollection = mongoClient.getDatabase(AppConfig.DATABASE).getCollection("conversations");
        conversationCollection.createIndex(Indexes.ascending("participants"));
        conversationCollection.createIndex(Indexes.ascending("conversationId"));
    }


    @Override
    public Optional<Conversation> getConversation(String conversationId) {
        Document document = conversationCollection.find(Filters.eq("conversationId", conversationId)).first();
        return mapToDomain(document);
    }

    @Override
    public void addConversation(Conversation conversation) {
        Document document = new Document("conversationId", conversation.getConversationId())
                                    .append("participants", conversation.getParticipants());
        conversationCollection.insertOne(document);
    }

    @Override
    public Optional<Conversation> getConversationBetween(String buyer, String seller) {
        Document document = conversationCollection.find(Filters.and(Filters.in("participants", buyer),
                                                                    Filters.in("participants", seller)))
                                                    .first();
        return mapToDomain(document);
    }

    private Optional<Conversation> mapToDomain(Document document) {
        if(document != null) {
            Conversation conversation = new Conversation(document.getString("conversationId"));
            conversation.setParticipants((List<String>) document.get("participants"));
            return Optional.of(conversation);
        }
        return Optional.empty();
    }
}
