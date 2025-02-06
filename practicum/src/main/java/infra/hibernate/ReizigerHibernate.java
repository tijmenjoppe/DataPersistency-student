package infra.hibernate;

import domain.IReizigerDao;
import domain.Reiziger;
import jakarta.persistence.EntityManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReizigerHibernate implements IReizigerDao {

    private EntityManager entityManager;

    public ReizigerHibernate(EntityManager entityManager) {

    }

    @Override
    public void save(Reiziger reiziger) throws SQLException {
    }

    @Override
    public void update(Reiziger reiziger) throws SQLException {
    }

    @Override
    public void delete(Reiziger reiziger) throws SQLException {
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Reiziger> findByGeboorteDatum(Date date) {
        return null;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        return null;
    }
}
