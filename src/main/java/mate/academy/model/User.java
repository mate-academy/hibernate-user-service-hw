package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private byte[] salts;

    @ManyToOne
    @JoinColumn(name = "movie_session_id")
    private MovieSession movieSession;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalts() {
        return salts;
    }

    public void setSalts(byte[] salts) {
        this.salts = salts;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && Arrays.equals(salts, user.salts);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(email, password);
        result = 31 * result + Arrays.hashCode(salts);
        return result;
    }

    @Override
    public String toString() {
        return "User{"
                + "email='" + email + '\''
                + ", password='" + password + '\''
                + ", salts=" + Arrays.toString(salts) + '}';
    }
}
