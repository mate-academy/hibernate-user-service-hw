package mate.academy.dao;

public interface UserDao {
    User add(User user);

    Optional<User> findByEmail(String email);
}
