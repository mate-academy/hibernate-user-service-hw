package mate.academy.service;

public interface AbstractService<T> {
    T get(Long id);

    T add(T object);
}
