package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Movie extends AbstractEntity {
    private String title;
    private String description;

    public Movie(String title) {
        this.title = title;
    }
}
