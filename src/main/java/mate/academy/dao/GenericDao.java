package mate.academy.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, I> {
    T add(T entity);

    Optional<T> findById(I id);

    List<T> findAll();
}
