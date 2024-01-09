package mate.academy.service;

import java.util.List;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.model.CinemaHall;

public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id) throws EntityNotFoundException;
    
    List<CinemaHall> getAll() throws EntityNotFoundException;
}
