package mate.academy.model;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private byte[] salt;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public byte[] getSalt() {
        return salt;
    }

    public User setSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", login='" + email + '\''
                + ", password='" + password + '\''
                + ", salt=" + Arrays.toString(salt)
                + '}';
    }
}
