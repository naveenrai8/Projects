package com.nr.mq.mapper;

import com.nr.mq.model.Message;
import com.nr.mq.model.MessagesResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageQueueMapper {

    public Message convertFrom(String messageContent, String clientId) {
        return Message
                .builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .content(messageContent)
                .build();
    }

    public MessagesResponseDto convertTo(Message message) {
        return new MessagesResponseDto(message.getId(), message.getContent());
    }
}
