package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

public class Main {
    private static final String VALID_EMAIL = "user@com";
    private static final String INVALID_EMAIL = "null@com";
    private static final String PASS = "12345678";
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final AuthenticationService authServ =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);
    private static final UserService userService =
            (UserService) injector.getInstance(UserService.class);

    public static void main(String[] args) {
        try {
            authServ.register(VALID_EMAIL, PASS);
            System.out.println("User " + userService.findByEmail(VALID_EMAIL).get()
                    + " registered");
        } catch (RegistrationException e) {
            System.out.println("Couldn't register user");
        }

        try {
            authServ.login(VALID_EMAIL, PASS);
            System.out.println("User " + userService.findByEmail(VALID_EMAIL).get()
                    + " logged");
        } catch (AuthenticationException e) {
            System.out.println("Couldn't login user");
        }

        try {
            authServ.register(VALID_EMAIL, PASS);
        } catch (RegistrationException e) {
            System.out.println("Couldn't register user");
        }

        try {
            authServ.login(INVALID_EMAIL, PASS);
        } catch (AuthenticationException e) {
            System.out.println("Couldn't login user");
        }
        /*
        MovieService movieService = null;

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");

        CinemaHallService cinemaHallService = null;
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        System.out.println(cinemaHallService.getAll());
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        MovieSessionService movieSessionService = null;
        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);

        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
        fastAndFurious.getId(), LocalDate.now()));
        */
    }
}
