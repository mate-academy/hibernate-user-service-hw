package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector INJECTOR
            = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie snatch = new Movie("Snatch");
        snatch.setDescription("A famous movie about british guys");
        movieService.add(snatch);
        System.out.println(movieService.get(snatch.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession todayMovieSessionOne = new MovieSession();
        todayMovieSessionOne.setCinemaHall(secondCinemaHall);
        todayMovieSessionOne.setMovie(fastAndFurious);
        todayMovieSessionOne.setShowTime(LocalDateTime.now());

        MovieSession todayMovieSessionTwo = new MovieSession();
        todayMovieSessionTwo.setCinemaHall(firstCinemaHall);
        todayMovieSessionTwo.setMovie(snatch);
        todayMovieSessionTwo.setShowTime(LocalDateTime.now().plusHours(1));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        MovieSessionService movieSessionService = (MovieSessionService) INJECTOR
                .getInstance(MovieSessionService.class);
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(todayMovieSessionOne);
        movieSessionService.add(todayMovieSessionTwo);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now()));
        System.out.println(movieSessionService.findAvailableSessions(
                snatch.getId(), LocalDate.now()));

        AuthenticationService authenticationService =
                (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);
        System.out.println(authenticationService.register("postfish@gmail.com",
                "CoolPassword1#"));
        System.out.println(authenticationService.register("mates@gmail.com",
                "CoolPassword123#"));

        //will be AuthenticationException
        //authenticationService.register("postfish@gmail.com", "CoolPassword2#");

        System.out.println(authenticationService.login("postfish@gmail.com",
                "CoolPassword1#"));
        System.out.println(authenticationService.login("mates@gmail.com",
                "CoolPassword123#"));

        //will be AuthenticationException
        //authenticationService.login("mates@gmail.com", "CoolPassword1#");
    }
}
