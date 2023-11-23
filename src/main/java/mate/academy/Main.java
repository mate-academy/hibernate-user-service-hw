package mate.academy;

import mate.academy.dao.UserDao;
import mate.academy.dao.impl.UserDaoImpl;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HashUtil;

public class Main {
    @Inject
    private AuthenticationService authenticationService;


    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl();
        User user = new User();
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword("Qwerty", user.getSalt()));
        user.setEmail("rer@gg.ry");
        userDao.add(user);
        User user2 = new User();
        user2.setSalt(HashUtil.getSalt());
        user2.setPassword(HashUtil.hashPassword("qwerty", user2.getSalt()));
        user2.setEmail("re@gg.ry");
        userDao.add(user2);
        //        MovieService movieService = null;
//
//        Movie fastAndFurious = new Movie("Fast and Furious");
//        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
//        movieService.add(fastAndFurious);
//        System.out.println(movieService.get(fastAndFurious.getId()));
//        movieService.getAll().forEach(System.out::println);
//
//        CinemaHall firstCinemaHall = new CinemaHall();
//        firstCinemaHall.setCapacity(100);
//        firstCinemaHall.setDescription("first hall with capacity 100");
//
//        CinemaHall secondCinemaHall = new CinemaHall();
//        secondCinemaHall.setCapacity(200);
//        secondCinemaHall.setDescription("second hall with capacity 200");
//
//        CinemaHallService cinemaHallService = null;
//        cinemaHallService.add(firstCinemaHall);
//        cinemaHallService.add(secondCinemaHall);
//
//        System.out.println(cinemaHallService.getAll());
//        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
//
//        MovieSession tomorrowMovieSession = new MovieSession();
//        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
//        tomorrowMovieSession.setMovie(fastAndFurious);
//        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));
//
//        MovieSession yesterdayMovieSession = new MovieSession();
//        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
//        yesterdayMovieSession.setMovie(fastAndFurious);
//        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));
//
//        MovieSessionService movieSessionService = null;
//        movieSessionService.add(tomorrowMovieSession);
//        movieSessionService.add(yesterdayMovieSession);
//
//        System.out.println(movieSessionService.get(yesterdayMovieSession.getId()));
//        System.out.println(movieSessionService.findAvailableSessions(
//                        fastAndFurious.getId(), LocalDate.now()));
    }
}
