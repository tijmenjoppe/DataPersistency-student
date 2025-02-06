package infra.dao;

import domain.*;

import java.sql.*;
import java.util.List;

public class ProductDaoPsql implements IProductDao {

    public ProductDaoPsql(Connection connection) {

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
