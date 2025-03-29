package com.nr.mq.controller;

import com.nr.mq.model.MessagesResponseDto;
import com.nr.mq.service.MessageQueueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class MessageQueueController {

    private final static String CLIENT_ID_HEADER = "clientId";
    private final MessageQueueService service;

    @GetMapping()
    public ResponseEntity<List<MessagesResponseDto>> getMessages(
            @RequestHeader(CLIENT_ID_HEADER) String clientId,
            @RequestParam int count,
            @RequestParam(value = "leaseTimeInSeconds") Optional<Integer> leaseTimeInSeconds) {
        if (count <= 0) {
            throw new IllegalArgumentException("count must be greater than 0");
        }
        var messages = service.getMessagesByCount(count, clientId, leaseTimeInSeconds.orElseGet(() -> leaseTimeInSeconds.orElse(10)));
        return ResponseEntity.ok(messages);
    }

    @PostMapping()
    public ResponseEntity<?> publishMessage(@RequestBody String message) {
        service.addMessage(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteMessage(@RequestHeader("clientId") String clientId) {
        service.deleteMessages(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
