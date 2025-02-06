package infra.dao;

import domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDaoPsql implements IReizigerDao {

    private Connection connection;
    private IOvChipkaartDao ovChipkaartDao;
    private IAdresDao adresDao;

    public ReizigerDaoPsql(Connection connection) {

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
    public List<Reiziger> findByGeboorteDatum(Date date) throws SQLException {
        return null;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        return null;
    }

    public void setAdresDao(IAdresDao adresDaoPsql) {

    }

    public void setOvChipkaartDao(IOvChipkaartDao ovChipkaartDaoPsql) {

    }
}
