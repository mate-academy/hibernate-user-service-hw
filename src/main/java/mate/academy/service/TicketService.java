package mate.academy.service;

import java.util.List;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.model.Ticket;

public interface TicketService {
    Ticket add(Ticket ticket);

    Ticket get(Long id) throws EntityNotFoundException;

    List<Ticket> getAll() throws EntityNotFoundException;
}
