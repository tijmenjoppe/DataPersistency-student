package infra.hibernate;

import domain.IProductDao;
import domain.OvChipkaart;
import domain.Product;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoHibernate implements IProductDao {

    private EntityManager entityManager;

    public ProductDaoHibernate(EntityManager entityManager) {

    }


    @Override
    public void save(Product product) throws SQLException {
    }

    @Override
    public void update(Product product) throws SQLException {
    }

    @Override
    public void delete(Product product) throws SQLException {
    }

    @Override
    public Product findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Product> findByOvChipkaart(OvChipkaart ovChipkaart) throws SQLException {
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return null;
    }
}
