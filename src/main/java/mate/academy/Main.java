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
        fastAndFurious.setDescription("Street racing, heists and spies.");
        movieService.add(fastAndFurious);

        System.out.println("Movie: " + movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("first hall");
        cinemaHallService.add(hall1);

        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(200);
        hall2.setDescription("second hall");
        cinemaHallService.add(hall2);

        System.out.println(cinemaHallService.getAll());
        System.out.println("Hall by id: " + cinemaHallService.get(hall1.getId()));

        try {
            AuthenticationService authService =
                    (AuthenticationService) injector.getInstance(AuthenticationService.class);

            User newUser = authService.register("mail@test.com", "1234");
            System.out.println("Registered user: " + newUser);

            User logged = authService.login("mail@test.com", "1234");
            System.out.println("Logged in: " + logged);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MovieSession tomorrow = new MovieSession();
        tomorrow.setMovie(fastAndFurious);
        tomorrow.setCinemaHall(hall1);
        tomorrow.setShowTime(LocalDateTime.now().plusDays(1));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrow);

        MovieSession yesterday = new MovieSession();
        yesterday.setMovie(fastAndFurious);
        yesterday.setCinemaHall(hall1);
        yesterday.setShowTime(LocalDateTime.now().minusDays(1));
        movieSessionService.add(yesterday);

        System.out.println("Yesterday session: "
                + movieSessionService.get(yesterday.getId()));

        System.out.println("Available today: "
                + movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()));
    }
}
