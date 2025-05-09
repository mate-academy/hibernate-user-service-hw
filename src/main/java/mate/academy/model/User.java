package mate.academy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String login;
    @Column
    private String password;
    private byte[] salt;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    //    @Override
    //    public boolean equals(Object o) {
    //        if (o == null || getClass() != o.getClass()) return false;
    //        User user = (User) o;
    //        return Objects.equals(id, user.id) && Objects.equals(login, user.login)
    //                && Objects.equals(password, user.password)
    //                && Objects.deepEquals(salt, user.salt);
    //    }
    //
    //    @Override
    //    public int hashCode() {
    //        return Objects.hash(id, login, password, Arrays.hashCode(salt));
    //    }

    @Override
    public String toString() {
        return "User{"
                + "login='" + login + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
