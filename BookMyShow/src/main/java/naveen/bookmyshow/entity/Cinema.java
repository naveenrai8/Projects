package naveen.bookmyshow.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cinema {
    private final int id = UUID.randomUUID().hashCode();
    private String cinemaName;
}
