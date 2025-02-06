package p3OneToOneTests;

import domain.*;
import globals.Psql;
import infra.dao.AdresDaoPsql;
import infra.dao.OvChipkaartDaoPsql;
import infra.dao.ProductDaoPsql;
import infra.dao.ReizigerDaoPsql;
import org.junit.jupiter.api.*;
import util.AdresUtils;
import util.ReizigerUtils;
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class p3TestReizigerDao {
    private static SetupDatabase setupDatabase;
    private IAdresDao adresDao;
    private IReizigerDao reizigerDao;


    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        setupDatabase = new SetupDatabase(Psql.dbName);

        setupDatabase.initializeDb();
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        ReizigerDaoPsql reizigerDao = new ReizigerDaoPsql(setupDatabase.getConnection());
        this.reizigerDao = reizigerDao;
        adresDao = new AdresDaoPsql(setupDatabase.getConnection());
        reizigerDao.setAdresDao(adresDao);
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
    public void testAddReizigerWithAdres() throws SQLException {
        Adres adres = adresDao.findById(901);
        assertNull(adres);

        Adres adresToSave = AdresUtils.generateAdres(901);
        Reiziger reizigerToSave = ReizigerUtils.generateReiziger(12);
        adresToSave.setReiziger(reizigerToSave);
        reizigerToSave.setAdres(adresToSave);

        reizigerDao.save(reizigerToSave);

        Reiziger reiziger = reizigerDao.findById(12);
        Adres newAdres = reiziger.getAdres();

        assertAll(
                () -> AdresUtils.assertEquals(adresToSave, newAdres),
                () -> assertNotNull(reiziger),
                () -> ReizigerUtils.assertEquals(reizigerToSave, reiziger)
        );
    }

    @Test
    public void testUpdateReizigerWithAdres() throws SQLException {
        //update Reiziger has already been tested, we can limit the test to the adres

        Reiziger reiziger = reizigerDao.findById(4);
        Adres adres = reiziger.getAdres();

        assertAll(
                () -> assertEquals(4, adres.getAdresId()),
                () -> assertEquals("3817CH", adres.getPostcode()),
                () -> assertEquals("Arnhemseweg", adres.getStraat()),
                () -> assertEquals("Amersfoort", adres.getWoonplaats()),
                () -> assertEquals("4", adres.getHuisnummer())
        );

        adres.setWoonplaats("Woonplaats");
        adres.setStraat("Straat");
        adres.setPostcode("1111AA");
        adres.setHuisnummer("55B");
        reizigerDao.update(reiziger);

        Adres newAdres = adresDao.findById(4);

        AdresUtils.assertEquals(adres, newAdres);
    }

    @Test
    public void testDeleteReizigerWithAdres() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(1);
        Adres adres = reiziger.getAdres();

        assertAll(
                () -> assertNotNull(reiziger),
                () -> assertNotNull(adres)
        );

        reizigerDao.delete(reiziger);

        Reiziger deletedReiziger = reizigerDao.findById(1);
        Adres deletedAdres = adresDao.findById(1);

        assertAll(
                () -> assertNull(deletedReiziger),
                () -> assertNull(deletedAdres)
        );
    }
}
