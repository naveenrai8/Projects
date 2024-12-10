package naveen.messageQueueSql.runner;

import naveen.messageQueueSql.entity.Message;
import naveen.messageQueueSql.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MessageJdbcCommandLineRunner implements CommandLineRunner {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void run(String... args) throws Exception {
        this.messageRepository.insert(new Message(3, "Third message", "3"));
        this.messageRepository.insert(new Message(4, "Fourth message", "4"));

        this.messageRepository.delete(1);
        Message message = this.messageRepository.getById(3);
        System.out.println(message.toString());
    }
}
