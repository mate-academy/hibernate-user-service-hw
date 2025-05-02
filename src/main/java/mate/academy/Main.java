package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
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
        final AuthenticationService authService = (AuthenticationService)
                Injector.getInstance("mate.academy")
                        .getInstance(AuthenticationService.class);
        final MovieService movieService = (MovieService)
                Injector.getInstance("mate.academy")
                        .getInstance(MovieService.class);

        try {
            User registered = authService.register("user@example.com", "pass123");
            System.out.println("Zarejestrowano użytkownika: " + registered);
            User logged = authService.login("user@example.com", "pass123");
            System.out.println("Zalogowano użytkownika:     " + logged);
        } catch (RegistrationException | AuthenticationException e) {
            e.printStackTrace();
        }

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("Film akcji o wyścigach ulicznych, napadach i agentach.");
        movieService.add(fastAndFurious);
        System.out.println("Pobierz film: " + movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(m -> System.out.println("Lista filmów: " + m));

        final CinemaHallService cinemaHallService = (CinemaHallService)
                Injector.getInstance("mate.academy")
                        .getInstance(CinemaHallService.class);
        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("Pierwsza sala, 100 miejsc");
        cinemaHallService.add(firstHall);

        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(200);
        secondHall.setDescription("Druga sala, 200 miejsc");
        cinemaHallService.add(secondHall);

        System.out.println("Wszystkie sale: " + cinemaHallService.getAll());
        System.out.println("Sala o id=" + firstHall.getId() + ": "
                + cinemaHallService.get(firstHall.getId()));

        final MovieSessionService movieSessionService = (MovieSessionService)
                Injector.getInstance("mate.academy")
                        .getInstance(MovieSessionService.class);
        MovieSession tomorrowSession = new MovieSession();
        tomorrowSession.setMovie(fastAndFurious);
        tomorrowSession.setCinemaHall(firstHall);
        tomorrowSession.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(tomorrowSession);

        MovieSession yesterdaySession = new MovieSession();
        yesterdaySession.setMovie(fastAndFurious);
        yesterdaySession.setCinemaHall(firstHall);
        yesterdaySession.setShowTime(LocalDateTime.now().minusDays(1));
        movieSessionService.add(yesterdaySession);

        System.out.println("Sesja wczorajsza: "
                + movieSessionService.get(yesterdaySession.getId()));
        System.out.println("Dostępne sesje na dziś dla filmu id="
                + fastAndFurious.getId()
                + ": " + movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()));
    }
}
