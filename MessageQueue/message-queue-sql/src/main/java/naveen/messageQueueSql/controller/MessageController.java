package naveen.messageQueueSql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import naveen.messageQueueSql.entity.Message;
import naveen.messageQueueSql.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping()
    public List<Message> getMessage(@RequestParam("count") int count,
                                    @RequestParam(required = false, name = "consumer_id") String consumerId) {
        return this.messageService.getMessages(count, consumerId);
    }
}
