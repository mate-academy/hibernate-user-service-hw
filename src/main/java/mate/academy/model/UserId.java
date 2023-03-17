package mate.academy.model;

import java.io.Serializable;
import java.util.Objects;

class UserId implements Serializable {
    private Long id;
    private String email;

    public UserId() {
    }

    public UserId(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserId userId = (UserId) o;
        return id.equals(userId.id) && email.equals(userId.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
