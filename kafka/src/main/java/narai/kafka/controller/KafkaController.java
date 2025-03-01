package narai.kafka.controller;

import narai.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/send/{topic}")
    public String sendMessage(@PathVariable String topic, @RequestBody String message) {
        kafkaProducerService.sendMessage(topic, message);
        return "Message sent to topic: " + topic;
    }
}
