package infra.dao;

import domain.*;

import java.sql.*;
import java.util.List;

public class OvChipkaartDaoPsql implements IOvChipkaartDao {

    public OvChipkaartDaoPsql(Connection connection) {

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

    public void setProductDao(IProductDao productDao) {

    }
}
