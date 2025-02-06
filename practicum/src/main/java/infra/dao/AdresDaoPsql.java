package infra.dao;

import domain.Adres;
import domain.IAdresDao;
import domain.Reiziger;

import java.sql.*;
import java.util.List;

public class AdresDaoPsql implements IAdresDao {

    public AdresDaoPsql(Connection connection) {

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
