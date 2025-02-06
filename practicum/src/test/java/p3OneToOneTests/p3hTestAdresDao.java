package p3OneToOneTests;

import domain.Adres;
import domain.IAdresDao;
import domain.IReizigerDao;
import domain.Reiziger;
import globals.Hibernate;
import infra.hibernate.AdresHibernate;
import infra.hibernate.ReizigerHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import util.AdresUtils;
import util.ReizigerUtils;
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class p3hTestAdresDao {
    private static SetupDatabase setupDatabase;
    private IAdresDao adresDao;
    private IReizigerDao reizigerDao;
    private EntityManager entityManager;

    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        setupDatabase = new SetupDatabase(Hibernate.dbName);

        setupDatabase.initializeDb();
    }

    @BeforeEach
    public void beforeEach() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(globals.Hibernate.persistanceUnitName);
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        adresDao = new AdresHibernate(entityManager);
        reizigerDao = new ReizigerHibernate(entityManager);
    }

    @AfterEach
    public void afterEach() throws SQLException {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
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
                () -> assertEquals("Jaarbeursplein", jaarbeursplein.getStraat()),
                () -> assertNotNull(jaarbeursplein.getReiziger())
        );
    }

    @Test
    public void testAdresDaoFind() throws SQLException {
        Adres adres = adresDao.findById(2);

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
    public void testUpdateAttachedAdres() throws SQLException {
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
        adresDao.update(adres);

        Adres newAdres = adresDao.findById(4);

        AdresUtils.assertEquals(adres, newAdres);
    }
}
