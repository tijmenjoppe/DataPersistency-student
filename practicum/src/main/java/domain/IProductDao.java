package domain;

import java.sql.SQLException;
import java.util.List;

public interface IProductDao {
    void save(Product product) throws SQLException;

    void update(Product product) throws SQLException;

    void delete(Product product) throws SQLException;

    Product findById(int id) throws SQLException;

    List<Product> findByOvChipkaart(OvChipkaart ovChipkaart) throws SQLException;

    List<Product> findAll() throws SQLException;
}
