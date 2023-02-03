package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHallService {
    CinemaHall add(CinemaHall entity);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
