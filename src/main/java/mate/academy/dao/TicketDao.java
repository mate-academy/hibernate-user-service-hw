package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Ticket;

public interface TicketDao {
    Ticket add(Ticket ticket);

    Optional<Ticket> get(Long id);

    List<Ticket> getAll();
}
