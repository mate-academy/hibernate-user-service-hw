package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.TicketDao;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Ticket;
import mate.academy.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
    @Inject
    private TicketDao ticketDao;

    @Override
    public Ticket add(Ticket ticket) {
        return ticketDao.add(ticket);
    }

    @Override
    public Ticket get(Long id) throws EntityNotFoundException {
        return ticketDao.get(id).orElseThrow(() ->
                new EntityNotFoundException("Cannot find ticket by id: " + id));
    }

    @Override
    public List<Ticket> getAll() throws EntityNotFoundException {
        List<Ticket> tickets = ticketDao.getAll();
        if (tickets.isEmpty()) {
            throw new EntityNotFoundException("Cannot find any ticket");
        }
        return tickets;
    }
}
