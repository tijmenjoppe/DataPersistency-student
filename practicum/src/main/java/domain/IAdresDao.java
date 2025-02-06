package domain;

import java.sql.SQLException;
import java.util.List;

public interface IAdresDao {
    void save(Adres adres) throws SQLException;

    void update(Adres adres) throws SQLException;

    void delete(Adres adres) throws SQLException;

    Adres findById(int id) throws SQLException;

    Adres findByReiziger(Reiziger reiziger) throws SQLException;

    List<Adres> findAll() throws SQLException;
}
