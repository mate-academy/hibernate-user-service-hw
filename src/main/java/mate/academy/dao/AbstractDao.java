package mate.academy.dao;

import java.util.Optional;

public interface AbstractDao<T> {
    T add(T movie);

    Optional<T> get(Long id);
}
