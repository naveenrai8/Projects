package naveen.messageQueueSql.service;

import naveen.messageQueueSql.entity.Message;
import naveen.messageQueueSql.repository.MessageRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages(int count, String clientId) {
        if (Strings.isBlank(clientId)) {
            clientId = UUID.randomUUID().toString();
        }

        return this.messageRepository.getMessages(count);
    }
}
