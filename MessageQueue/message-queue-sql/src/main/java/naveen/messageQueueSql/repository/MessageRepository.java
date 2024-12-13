package naveen.messageQueueSql.repository;

import naveen.messageQueueSql.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MessageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_QUERY = """
             insert into message (id, content, consumer_id)
             values (?,?,?)
            """;

    private static final String DELETE_QUERY = """
             delete from message where id = ?
            """;

    private static final String GET_QUERY = """
             select * from  message where id = ?
            """;

    private static final String GET_QUERY_COUNT_FROM_LAST =
            """
                    SELECT * FROM MESSAGE
                    where consumer_id is null
                    order by id desc limit ?
            """;

    private static final String UPDATE_CONSUMER_ID_QUERY =
            """
                update message set consumer_id = ?
                        where id = ?
            """;

    public void insert(Message message) {
        this.jdbcTemplate.update(INSERT_QUERY, message.getId(), message.getContent(), message.getConsumerId());
    }

    public void delete(long id) {
        this.jdbcTemplate.update(DELETE_QUERY, id);
    }

    public Message getById(long id) {
        return this.jdbcTemplate.queryForObject(GET_QUERY,
                new BeanPropertyRowMapper<>(Message.class),
                id);
    }

    @Transactional
    public List<Message> getMessages(int count, String consumerId) {
        List<Message> messages = this.jdbcTemplate.query(GET_QUERY_COUNT_FROM_LAST,
                new BeanPropertyRowMapper<>(Message.class),
                count);
        for (Message message : messages) {
            message.setConsumerId(consumerId);
            this.jdbcTemplate.update(UPDATE_CONSUMER_ID_QUERY, consumerId,  message.getId());
        }
        return messages;
    }
}
