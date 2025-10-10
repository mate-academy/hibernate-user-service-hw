package mate.academy.dao.impl;

import mate.academy.dao.AbstractDao;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

@Dao
public class CinemaHallDaoImpl extends AbstractDao<CinemaHall, Long> implements CinemaHallDao {

    public CinemaHallDaoImpl() {
        super(CinemaHall.class);
    }
}
