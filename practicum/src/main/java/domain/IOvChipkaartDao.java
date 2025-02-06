package domain;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IOvChipkaartDao {
    void save(OvChipkaart ovChipkaart) throws SQLException;
    void update(OvChipkaart ovChipkaart) throws SQLException;
    void delete(OvChipkaart ovChipkaart) throws SQLException;
    OvChipkaart findById(int id) throws SQLException;

    List<OvChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

    List<OvChipkaart> findAll() throws SQLException;
}
