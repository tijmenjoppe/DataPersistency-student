package p4OneToManyTests;

import domain.*;
import globals.Hibernate;
import infra.hibernate.AdresHibernate;
import infra.hibernate.OvChipkaartDaoHibernate;
import infra.hibernate.ReizigerHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import util.AdresUtils;
import util.OvChipkaartUtils;
import util.ReizigerUtils;
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class p4hTestReizigerDao {
    private static SetupDatabase setupDatabase;
    private IAdresDao adresDao;
    private IReizigerDao reizigerDao;
    private IOvChipkaartDao ovChipkaartDao;

    private EntityManager entityManager;


    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        setupDatabase = new SetupDatabase(Hibernate.dbName);

        setupDatabase.initializeDb();
    }

    @BeforeEach
    public void beforeEach() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(Hibernate.persistanceUnitName);
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        adresDao = new AdresHibernate(entityManager);
        reizigerDao = new ReizigerHibernate(entityManager);
        ovChipkaartDao = new OvChipkaartDaoHibernate(entityManager);
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
    public void testAddReizigerWithOvChipkaarten() throws SQLException {
        OvChipkaart ovChipkaart1 = ovChipkaartDao.findById(1);
        OvChipkaart ovChipkaart2 = ovChipkaartDao.findById(2);
        OvChipkaart ovChipkaart3 = ovChipkaartDao.findById(3);
        assertNull(ovChipkaart1);
        assertNull(ovChipkaart2);
        assertNull(ovChipkaart3);

        List<OvChipkaart> ovChipkaarten = new ArrayList<>();
        ovChipkaarten.add(OvChipkaartUtils.generateOvChipkaart(1));
        ovChipkaarten.add(OvChipkaartUtils.generateOvChipkaart(2));
        ovChipkaarten.add(OvChipkaartUtils.generateOvChipkaart(3));
        Reiziger reizigerToSave = ReizigerUtils.generateReiziger(12);

        for (OvChipkaart ovChipkaart : ovChipkaarten) {
            ovChipkaart.setReiziger(reizigerToSave);
        }
        reizigerToSave.setOvChipkaart(ovChipkaarten);

        reizigerDao.save(reizigerToSave);

        OvChipkaart newOvChipkaart1 = ovChipkaartDao.findById(1);
        OvChipkaart newOvChipkaart2 = ovChipkaartDao.findById(2);
        OvChipkaart newOvChipkaart3 = ovChipkaartDao.findById(3);
        Reiziger reiziger = newOvChipkaart1.getReiziger();

        assertAll(
                () -> OvChipkaartUtils.assertEquals(ovChipkaarten.get(0), newOvChipkaart1),
                () -> OvChipkaartUtils.assertEquals(ovChipkaarten.get(1), newOvChipkaart2),
                () -> OvChipkaartUtils.assertEquals(ovChipkaarten.get(2), newOvChipkaart3),
                () -> assertNotNull(reiziger),
                () -> ReizigerUtils.assertEquals(reizigerToSave, reiziger)
        );
    }

    @Test
    public void testUpdateReizigerWithOvChipkaarten() throws SQLException {
        //update Reiziger has already been tested, we can limit the test to the ovChipkaart

        Reiziger reiziger = reizigerDao.findById(3);
        OvChipkaart ovChipkaart = reiziger.getOvChipkaart().get(0);


        assertAll(
                () -> assertEquals(68514, ovChipkaart.getKaartNummer()),
                () -> assertEquals(Date.valueOf("2020-03-31"), ovChipkaart.getGeldigTot()),
                () -> assertEquals(BigInteger.ONE, ovChipkaart.getKlasse()),
                () -> assertEquals(new BigDecimal("2.50"), ovChipkaart.getSaldo())
        );

        ovChipkaart.setKlasse(BigInteger.TWO);
        ovChipkaart.setGeldigTot(Date.valueOf("2021-01-01"));
        ovChipkaart.setSaldo(new BigDecimal("-1.50"));
        reizigerDao.update(reiziger);

        OvChipkaart updatedOvChipkaart = ovChipkaartDao.findById(68514);

        OvChipkaartUtils.assertEquals(ovChipkaart, updatedOvChipkaart);
    }

    @Test
    public void testDeleteReizigerWithOvChipkaarten() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(77);
        List<OvChipkaart> ovChipkaarten = reiziger.getOvChipkaart();
        OvChipkaart ovChipkaart1 = ovChipkaartDao.findById(12543);
        OvChipkaart ovChipkaart2 = ovChipkaartDao.findById(65427);

        assertAll(
                () -> assertNotNull(reiziger),
                () -> assertNotNull(ovChipkaart1),
                () -> assertNotNull(ovChipkaart2),
                () -> assertEquals(2, ovChipkaarten.size())
        );

        reizigerDao.delete(reiziger);
        entityManager.getTransaction().commit();

        Reiziger deletedReiziger = reizigerDao.findById(77);
        OvChipkaart deletedOvChipkaart1 = ovChipkaartDao.findById(12543);
        OvChipkaart deletedOvChipkaart2 = ovChipkaartDao.findById(65427);

        assertAll(
                () -> assertNull(deletedReiziger),
                () -> assertNull(deletedOvChipkaart1),
                () -> assertNull(deletedOvChipkaart2)
        );
    }

    @Test
    public void testUpdateReizigerAddOvChipkaart() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(2);
        List<OvChipkaart> ovChipkaarten = reiziger.getOvChipkaart();

        assertAll(
                () -> assertNotNull(reiziger),
                () -> assertEquals(3, ovChipkaarten.size())
        );

        OvChipkaart newOvChipkaart = OvChipkaartUtils.generateOvChipkaart(8);
        newOvChipkaart.setReiziger(reiziger);
        ovChipkaarten.add(newOvChipkaart);

        entityManager.getTransaction().commit();

        List<OvChipkaart> addedOvChipkaarten = ovChipkaartDao.findByReiziger(reiziger);

        assertAll(
                () -> assertEquals(4, addedOvChipkaarten.size()),
                () -> OvChipkaartUtils.assertEquals(newOvChipkaart, addedOvChipkaarten.stream().filter(x -> x.getKaartNummer() == 8).findAny().get())
        );
    }
}
