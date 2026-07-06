package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription(
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");
        cinemaHallService.add(firstCinemaHall);

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(
                        MovieSessionService.class);

        movieSessionService.add(tomorrowMovieSession);

        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(
                        AuthenticationService.class);

        try {
            User user = authenticationService.register(
                    "admin@gmail.com",
                    "123456");
            System.out.println(user);

            User loggedUser = authenticationService.login(
                    "admin@gmail.com",
                    "123456");
            System.out.println(loggedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
