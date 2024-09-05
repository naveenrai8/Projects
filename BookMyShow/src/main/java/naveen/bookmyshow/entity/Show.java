package naveen.bookmyshow.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Show {
    private final int id = UUID.randomUUID().hashCode();
    private int movieId;
    private LocalDateTime showTime;
    private int cinemaId;
}
