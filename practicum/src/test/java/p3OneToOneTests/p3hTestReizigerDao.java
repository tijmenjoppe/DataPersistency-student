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

import static org.junit.jupiter.api.Assertions.*;

public class p3hTestReizigerDao {
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

        Adres newAdres = adresDao.findById(901);
        Reiziger reiziger = newAdres.getReiziger();

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
        entityManager.getTransaction().commit();

        Reiziger deletedReiziger = reizigerDao.findById(1);
        Adres deletedAdres = adresDao.findById(1);

        assertAll(
                () -> assertNull(deletedReiziger),
                () -> assertNull(deletedAdres)
        );
    }
}
