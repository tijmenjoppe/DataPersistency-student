package infra.hibernate;

import domain.IOvChipkaartDao;
import domain.OvChipkaart;
import domain.Reiziger;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.List;

public class OvChipkaartDaoHibernate implements IOvChipkaartDao {

    public OvChipkaartDaoHibernate(EntityManager entityManager) {

    }


    @Override
    public void save(OvChipkaart ovChipkaart) throws SQLException {
    }

    @Override
    public void update(OvChipkaart ovChipkaart) throws SQLException {
    }

    @Override
    public void delete(OvChipkaart ovChipkaart) throws SQLException {
    }

    @Override
    public OvChipkaart findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<OvChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        return null;
    }

    @Override
    public List<OvChipkaart> findAll() throws SQLException {
        return null;
    }
}
