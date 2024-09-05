package naveen.bookmyshow.service;

import naveen.bookmyshow.entity.Show;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShowServiceImpl implements ShowService {
    private final Map<Integer, Show> shows;

    public ShowServiceImpl() {
        shows = new HashMap<>();
    }
}
