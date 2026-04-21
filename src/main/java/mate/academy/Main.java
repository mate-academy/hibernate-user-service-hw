package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) {
        // MovieService movieService = null;

        // Movie fastAndFurious = new Movie("Fast and Furious");
        // fastAndFurious.setDescription("An action film about street racing, heists,
        // and spies.");
        // movieService.add(fastAndFurious);
        // System.out.println(movieService.get(fastAndFurious.getId()));
        // movieService.getAll().forEach(System.out::println);

        // CinemaHall firstCinemaHall = new CinemaHall();
        // firstCinemaHall.setCapacity(100);
        // firstCinemaHall.setDescription("first hall with capacity 100");

        // CinemaHall secondCinemaHall = new CinemaHall();
        // secondCinemaHall.setCapacity(200);
        // secondCinemaHall.setDescription("second hall with capacity 200");

        // CinemaHallService cinemaHallService = null;
        // cinemaHallService.add(firstCinemaHall);
        // cinemaHallService.add(secondCinemaHall);

        // System.out.println(cinemaHallService.getAll());
        // System.out.println(cinemaHallService.get(firstCinemaHall.getId()));

        // MovieSession tomorrowMovieSession = new MovieSession();
        // tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        // tomorrowMovieSession.setMovie(fastAndFurious);
        // tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));

        // MovieSession yesterdayMovieSession = new MovieSession();
        // yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        // yesterdayMovieSession.setMovie(fastAndFurious);
        // yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        // MovieSessionService movieSessionService = null;
        // movieSessionService.add(tomorrowMovieSession);
        // movieSessionService.add(yesterdayMovieSession);

        // System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
        // System.out.println(movieSessionService.findAvailableSessions(
        // fastAndFurious.getId(), LocalDate.now()));

        AuthenticationService authService = (AuthenticationService) Injector
                .getInstance("mate.academy")
                .getInstance(AuthenticationService.class);
        try {
            authService
                    .register("test@example.com", "1234");
            System.out.println("Registrastion succsesfull");

            System.out.println("Login status: " + authService.login("test@example.com", "1234"));

            authService.login("test@example.com", "wrong_password");
        } catch (Exception e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
