package com.nr.mq.service;

import com.nr.mq.mapper.MessageQueueMapper;
import com.nr.mq.model.Message;
import com.nr.mq.model.MessagesResponseDto;
import com.nr.mq.repository.MessageQueueRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageQueueService {

    private final MessageQueueRepository repository;
    private final MessageQueueMapper mapper;

    public List<MessagesResponseDto> getMessagesByCount(int count, String clientId) {
        var messages = repository.getMessages(clientId, count);
        return messages.stream().map(
                m ->
                        new MessagesResponseDto(m.getId(), m.getContent())
        ).toList();
    }

    public String registerClient() {
        return UUID.randomUUID().toString();
    }

    public void addMessage(String message) {
        repository.insertMessage(
                Message.builder()
                        .content(message)
                        .id(UUID.randomUUID().toString())
                        .build()
        );
    }

    public void deleteMessages(String clientId) {
        repository.deleteMessages(clientId);
    }
}
