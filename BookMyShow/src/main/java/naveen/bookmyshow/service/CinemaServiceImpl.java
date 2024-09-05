package naveen.bookmyshow.service;

import naveen.bookmyshow.entity.Show;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final ShowService showService;
    private final Map<Integer, List<Integer>> cinemaToShowsMap;

    public CinemaServiceImpl(ShowService showService) {
        this.showService = showService;
        cinemaToShowsMap = new HashMap<>();
    }

    @Override
    public int getSeats(int showId, int cinemaId) {
        return this.showService.getAvailableSeats(this.cinemaToShowsMap.get(cinemaId).get(showId));
    }

    @Override
    public List<Show> getAllShows(int cinemaId, LocalDate localDate) {
        if (!cinemaToShowsMap.containsKey(movieId)) {
            throw new InvalidCinemaException(MessageFormat.format("Cinema with id {0} does not exists", cinemaId));
        }
        List<Show> shows = new ArrayList<>();
        for (int showId : this.cinemaToShowsMap.get(cinemaId)) {
            Show show = this.showService.getShow(showId);
            if (show.getShowTime().after(local)) {
                shows.add();
            }
        }
        return shows;
    }
}
