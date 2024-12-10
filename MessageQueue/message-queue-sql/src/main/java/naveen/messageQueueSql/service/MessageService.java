package naveen.messageQueueSql.service;

import naveen.messageQueueSql.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public boolean getMessages(int count){
        return getMessages(count, UUID.randomUUID().toString());
    }

    public boolean getMessages(int count, String clientId){
        return false;
    }
}
