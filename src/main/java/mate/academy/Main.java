package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    
    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall(120, "3D");
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(movieService.get(1L));

        CinemaHall cinemaHallSecond = new CinemaHall(100, "2D");
        cinemaHallService.add(cinemaHallSecond);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(cinemaHallService.get(1L));

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);

        movieSessionService.add(movieSession);

        MovieSession movieSessionSecond = new MovieSession();
        movieSessionSecond.setMovie(fastAndFurious);
        movieSessionSecond.setCinemaHall(cinemaHallSecond);
        movieSessionSecond.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionSecond);
        movieSessionService.get(movieSessionSecond.getId());

        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
