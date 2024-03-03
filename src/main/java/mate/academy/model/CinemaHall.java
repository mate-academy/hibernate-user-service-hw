package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cinema_halls")
@Getter
@Setter
@ToString(callSuper = true)
public class CinemaHall extends AbstractEntity {
    private int capacity;
    private String description;
}
