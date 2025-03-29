package com.nr.mq.repository;

import com.nr.mq.mapper.MessageQueueMapper;
import com.nr.mq.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MessageQueueRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final ZoneOffset ZONE_OFFSET_NOW = ZoneOffset.UTC;
    private static final String SET_MESSAGES =
            """
                            insert into messageQueue(id, content) values
                            (
                            	?, ?
                            )
                    """;
    private static final String UPDATE_CLIENT_MESSAGES =
            """
                           update messageQueue
                           set clientId = ?, leaseTill = ? where id = ?;
                    """;

    private static final String DELETE_MESSAGES =
            """
                            delete from messageQueue
                            where clientId = ?
                    """;

    private static final String GET_MESSAGES =
            """
                            select id, content, clientId from messageQueue
                            where clientId is null or leaseTill < (?)
                            limit ? for update skip locked
                    """;

    public void insertMessage(Message messageDto) {
        jdbcTemplate.update(SET_MESSAGES, messageDto.getId(), messageDto.getContent());
    }

    public void deleteMessages(String clientId) {
        var rows = jdbcTemplate.update(DELETE_MESSAGES, clientId);
        log.info("number of rows affected {}", rows);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Message> getMessages(String clientId, int count, int leaseTimeInSeconds) {
        List<Message> messages = new ArrayList<>();

        var rows = jdbcTemplate.query(GET_MESSAGES,
                new BeanPropertyRowMapper<>(Message.class), LocalDateTime.now().toEpochSecond(ZONE_OFFSET_NOW),  count);
        log.info("Time: {}",  LocalDateTime.now().plusSeconds(leaseTimeInSeconds).toEpochSecond(ZONE_OFFSET_NOW));
        rows.forEach(
                row -> {
                    messages.add(row);
                    jdbcTemplate.update(UPDATE_CLIENT_MESSAGES, clientId,
                            LocalDateTime.now().plusSeconds(leaseTimeInSeconds).toEpochSecond(ZONE_OFFSET_NOW),
                            row.getId());
                }
        );
        return messages;

    }
}
