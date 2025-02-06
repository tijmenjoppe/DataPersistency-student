package p2SingularObjectTests;

import domain.IReizigerDao;
import domain.Reiziger;
import globals.Hibernate;
import infra.hibernate.ReizigerHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import util.ReizigerUtils;
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class p2hTestReizigerDao {

    private static SetupDatabase setupDatabase;
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

        reizigerDao = new ReizigerHibernate(entityManager);
    }

    @AfterEach
    public void afterEach() throws SQLException {
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public static void after() throws SQLException {
        setupDatabase.getConnection().endRequest();
        setupDatabase.getConnection().close();
        setupDatabase.clean();
    }

    @Test
    public void testReizigerDaoInitialState() throws SQLException {
        List<Reiziger> reizigers = reizigerDao.findAll();
        Reiziger gVanRijn = reizigers.stream().filter(x -> x.getReizigerId() == 1).findAny().get();

        assertAll(
                () -> assertTrue(reizigers.size() > 1),
                () -> assertEquals(1, gVanRijn.getReizigerId()),
                () -> assertEquals("G", gVanRijn.getVoorletters()),
                () -> assertEquals("van", gVanRijn.getTussenvoegsel()),
                () -> assertEquals("Rijn", gVanRijn.getAchternaam()),
                () -> assertEquals(Date.valueOf("2002-09-17"), gVanRijn.getGeboortedatum())
        );
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
    public void testUpdateAttachedReiziger() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(3);

        assertAll(
                () -> assertEquals(3, reiziger.getReizigerId()),
                () -> assertEquals("H", reiziger.getVoorletters()),
                () -> assertNull(reiziger.getTussenvoegsel()),
                () -> assertEquals("Lubben", reiziger.getAchternaam()),
                () -> assertEquals(Date.valueOf("1998-08-11"), reiziger.getGeboortedatum())
        );

        reiziger.setVoorletters("J");
        reiziger.setTussenvoegsel("de");
        reiziger.setAchternaam("Jong");
        reiziger.setGeboortedatum(Date.valueOf("1988-04-03"));
        reizigerDao.update(reiziger);

        Reiziger newReiziger = reizigerDao.findById(3);

        ReizigerUtils.assertEquals(reiziger, newReiziger);
    }

    @Test
    public void testDeleteAttachedReiziger() throws SQLException {
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
