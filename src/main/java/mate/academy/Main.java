import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.security.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws RegistrationException, AuthenticationException {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        @@ -27,7 +33,8 @@ public static void main(String[] args) {
            secondCinemaHall.setCapacity(200);
            secondCinemaHall.setDescription("second hall with capacity 200");

            CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
            cinemaHallService.add(firstCinemaHall);
            cinemaHallService.add(secondCinemaHall);

            @@ -44,12 +51,19 @@ public static void main(String[] args) {
                yesterdayMovieSession.setMovie(fastAndFurious);
                yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

                MovieSessionService movieSessionService = (MovieSessionService) injector
                    .getInstance(MovieSessionService.class);
                movieSessionService.add(tomorrowMovieSession);
                movieSessionService.add(yesterdayMovieSession);

                System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
                System.out.println(movieSessionService.findAvailableSessions(
                    fastAndFurious.getId(), LocalDate.now()));

                AuthenticationService authenticationService = (AuthenticationService) injector
                    .getInstance(AuthenticationService.class);

                System.out.println(authenticationService.register("dog@ukr.net", "doggy12345"));
                System.out.println(authenticationService.login("dog@ukr.net", "doggy12345"));
            }
        }