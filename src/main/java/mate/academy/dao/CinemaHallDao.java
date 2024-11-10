package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

@Dao
public interface CinemaHallDao {
    CinemaHall add(CinemaHall cinemaHall);

    Optional<CinemaHall> get(Long id);

    List<CinemaHall> getAll();
}
