package p2SingularObjectTests;

import domain.Reiziger;
import globals.Psql;
import infra.dao.AdresDaoPsql;
import infra.dao.OvChipkaartDaoPsql;
import infra.dao.ProductDaoPsql;
import infra.dao.ReizigerDaoPsql;
import org.junit.jupiter.api.*;
import util.ReizigerUtils;
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class p2TestReizigerDao {

    private static SetupDatabase setupDatabase;
    ReizigerDaoPsql reizigerDao;

    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        setupDatabase = new SetupDatabase(Psql.dbName);

        setupDatabase.initializeDb();
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        reizigerDao = new ReizigerDaoPsql(setupDatabase.getConnection());
        reizigerDao.setAdresDao(new AdresDaoPsql(setupDatabase.getConnection()));
        OvChipkaartDaoPsql ovChipkaartDao = new OvChipkaartDaoPsql(setupDatabase.getConnection());
        ovChipkaartDao.setProductDao(new ProductDaoPsql(setupDatabase.getConnection()));
        reizigerDao.setOvChipkaartDao(ovChipkaartDao);
        setupDatabase.getConnection().beginRequest();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        setupDatabase.getConnection().commit();
    }

    @AfterAll
    public static void after() throws SQLException {
        setupDatabase.getConnection().endRequest();
        setupDatabase.getConnection().close();
        setupDatabase.clean();
    }

    @Test
    public void testReizigerDaoFind() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(2);

        assertAll(
                () -> assertEquals(2, reiziger.getReizigerId()),
                () -> assertEquals("B", reiziger.getVoorletters()),
                () -> assertEquals("van", reiziger.getTussenvoegsel()),
                () -> assertEquals("Rijn", reiziger.getAchternaam()),
                () -> assertEquals(Date.valueOf("2002-10-22"), reiziger.getGeboortedatum())
        );
    }

    @Test
    public void testReizigerDoesntExist() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(10);
        assertNull(reiziger);
    }

    @Test
    public void testAddReiziger() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(9);
        assertNull(reiziger);

        Reiziger reizigerToSave = ReizigerUtils.generateReiziger(9);
        reizigerDao.save(reizigerToSave);
        Reiziger newReiziger = reizigerDao.findById(9);
        ReizigerUtils.assertEquals(reizigerToSave, newReiziger);
    }

    @Test
    public void testUpdateReiziger() throws SQLException {
        Reiziger reizigerToSave = ReizigerUtils.generateReiziger(4);
        reizigerDao.update(reizigerToSave);
        Reiziger newReiziger = reizigerDao.findById(4);
        ReizigerUtils.assertEquals(reizigerToSave, newReiziger);
    }

    @Test
    public void testDeleteReiziger() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(8);
        assertNotNull(reiziger);

        reizigerDao.delete(reiziger);
        reiziger = reizigerDao.findById(8);
        assertNull(reiziger);
    }

    @Test
    public void testFindByGeboorteDatum() throws SQLException {
        List<Reiziger> reizigers = reizigerDao.findByGeboorteDatum(Date.valueOf("2002-09-17"));

        assertAll(
                () -> assertEquals(2, reizigers.size()),
                () -> assertTrue(reizigers.stream().anyMatch(x -> x.getReizigerId() == 1)),
                () -> assertTrue(reizigers.stream().anyMatch(x -> x.getReizigerId() == 7))
        );
    }
}
