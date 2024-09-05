package naveen.bookmyshow.service;

import naveen.bookmyshow.entity.Show;

import java.time.LocalDate;
import java.util.List;

public interface CinemaService {
    int getSeats(int movieId, int cinemaId);

    List<Show> getAllShows(int cinemaId, LocalDate localDate);
}
