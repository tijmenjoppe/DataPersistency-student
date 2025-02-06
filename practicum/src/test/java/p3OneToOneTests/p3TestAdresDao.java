package p3OneToOneTests;

import domain.Adres;
import domain.IAdresDao;
import domain.IReizigerDao;
import domain.Reiziger;
import globals.Hibernate;
import globals.Psql;
import infra.dao.AdresDaoPsql;
import infra.dao.OvChipkaartDaoPsql;
import infra.dao.ProductDaoPsql;
import infra.dao.ReizigerDaoPsql;
import infra.hibernate.AdresHibernate;
import infra.hibernate.ReizigerHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import util.AdresUtils;
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class p3TestAdresDao {
    private static SetupDatabase setupDatabase;
    private IAdresDao adresDao;
    private ReizigerDaoPsql reizigerDao;

    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        setupDatabase = new SetupDatabase(Psql.dbName);

        setupDatabase.initializeDb();
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        reizigerDao = new ReizigerDaoPsql(setupDatabase.getConnection());
        AdresDaoPsql adresDao = new AdresDaoPsql(setupDatabase.getConnection());
        reizigerDao.setAdresDao(adresDao);
        this.adresDao = adresDao;
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
    public void testAdresDaoInitialState() throws SQLException {
        List<Adres> adressen = adresDao.findAll();
        Adres jaarbeursplein = adressen.stream().filter(x -> x.getAdresId() == 2).findAny().get();

        assertAll(
                () -> assertTrue(adressen.size() > 1),
                () -> assertEquals(2, jaarbeursplein.getAdresId()),
                () -> assertEquals("3521AL", jaarbeursplein.getPostcode()),
                () -> assertEquals("6A", jaarbeursplein.getHuisnummer()),
                () -> assertEquals("Utrecht", jaarbeursplein.getWoonplaats()),
                () -> assertEquals("Jaarbeursplein", jaarbeursplein.getStraat())
        );
    }

    @Test
    public void testAdresDaoFind() throws SQLException {
        Adres adres = adresDao.findById(2);

        adres.setStraat("Jaarbeursplein");

        assertAll(
                () -> assertEquals(2, adres.getAdresId()),
                () -> assertEquals("3521AL", adres.getPostcode()),
                () -> assertEquals("6A", adres.getHuisnummer()),
                () -> assertEquals("Utrecht", adres.getWoonplaats())
        );
    }

    @Test
    public void testAdresDaoFindByReiziger() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(2);
        Adres adres = adresDao.findByReiziger(reiziger);

        assertAll(
                () -> assertEquals(2, adres.getAdresId()),
                () -> assertEquals("3521AL", adres.getPostcode()),
                () -> assertEquals("6A", adres.getHuisnummer()),
                () -> assertEquals("Utrecht", adres.getWoonplaats())
        );
    }

    @Test
    public void testAdresDoesntExist() throws SQLException {
        Adres adres = adresDao.findById(10);
        assertNull(adres);
    }

    @Test
    public void testUpdateAdres() throws SQLException {
        Adres adres = adresDao.findById(4);

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
        adres.setReiziger(reizigerDao.findById(4));
        adresDao.update(adres);

        Adres newAdres = adresDao.findById(4);

        AdresUtils.assertEquals(adres, newAdres);
    }

    @Test
    public void testDeleteAdres() throws SQLException {
        Adres adres = adresDao.findById(5);

        assertAll(
                () -> assertEquals(5, adres.getAdresId()),
                () -> assertEquals("3572WP", adres.getPostcode()),
                () -> assertEquals("Vermeulenstraat", adres.getStraat()),
                () -> assertEquals("Utrecht", adres.getWoonplaats()),
                () -> assertEquals("22", adres.getHuisnummer())
        );

        adresDao.delete(adres);

        Adres deletedAdres = adresDao.findById(5);

        assertNull(deletedAdres);
    }
}
