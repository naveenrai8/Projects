package com.nr.mq.model;

import java.util.List;

public record MessagesResponseDto(
        String messageId,
        String messages
) {
}
