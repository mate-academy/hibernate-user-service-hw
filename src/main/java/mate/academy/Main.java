package mate.academy;

import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws AuthenticationException, RegistrationException {
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

        final UserService userService =
                (UserService) injector.getInstance(UserService.class);

        User bob = new User("bob@gmail", "qwery");
        bob.setSalt(HashUtil.getSalt());
        bob.setPassword(HashUtil.hashPassword("qwery", bob.getSalt()));

        User alisa = new User("alise@gmail", "12345");
        bob.setSalt(HashUtil.getSalt());
        bob.setPassword(HashUtil.hashPassword("12345", bob.getSalt()));

        User john = new User("john@gmail", "helloword");
        bob.setSalt(HashUtil.getSalt());
        bob.setPassword(HashUtil.hashPassword("helloword", bob.getSalt()));
        //        userService.add(bob);
        //        userService.add(alisa);
        //        userService.add(john);

        userService.getAll().forEach(x -> System.out.println(x));
        // System.out.println(userService.findByEmail("john@gmail"));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        //System.out.println(authenticationService.login("alise@gmail", "12345"));

        // System.out.println(authenticationService.register("pony@gmail.com", "ponypony"));
        System.out.println(userService.findByEmail("pony@gmail.com"));
    }
}

