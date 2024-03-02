package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"password", "salt"})
public class User extends AbstractEntity {
    private String email;
    private String password;
    private byte[] salt;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
