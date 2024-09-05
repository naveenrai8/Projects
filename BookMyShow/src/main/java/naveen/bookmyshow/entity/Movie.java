package naveen.bookmyshow.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Movie {
    private final int id = UUID.randomUUID().hashCode();
    private String title;
    private Duration duration;
}
