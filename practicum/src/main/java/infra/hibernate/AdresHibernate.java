package infra.hibernate;

import domain.Adres;
import domain.Reiziger;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.List;

public class AdresHibernate implements domain.IAdresDao {

    public AdresHibernate(EntityManager entityManager) {

    }

    @Override
    public void save(Adres adres) throws SQLException {
    }

    @Override
    public void update(Adres adres) throws SQLException {
    }

    @Override
    public void delete(Adres adres) throws SQLException {
    }

    @Override
    public Adres findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        return null;
    }
}
