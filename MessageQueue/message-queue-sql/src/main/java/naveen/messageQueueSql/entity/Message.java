package naveen.messageQueueSql.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Setter
public class Message {
    private long id;
    private String content;
    private String  clientId;
}
