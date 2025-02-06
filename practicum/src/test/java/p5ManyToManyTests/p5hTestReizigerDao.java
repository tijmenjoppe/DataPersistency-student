package p5ManyToManyTests;

import domain.*;
import globals.Hibernate;
import infra.hibernate.OvChipkaartDaoHibernate;
import infra.hibernate.ProductDaoHibernate;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class p5hTestReizigerDao {
    private static SetupDatabase setupDatabase;
    private IOvChipkaartDao ovChipkaartDao;
    private IProductDao productDao;
    private IReizigerDao reizigerDao;

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

        ovChipkaartDao = new OvChipkaartDaoHibernate(entityManager);
        productDao = new ProductDaoHibernate(entityManager);
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
    public void testAddReizigerWithProductShouldAddProduct() throws SQLException {
        List<OvChipkaart> ovChipkaarten = new ArrayList<>();
        OvChipkaart ovChipkaart = OvChipkaartUtils.generateOvChipkaart(1);
        Product product = new Product();
        product.setBeschrijving("beschrijving");
        product.setPrijs(new BigDecimal("20.00"));
        product.setNaam("naam");
        ovChipkaarten.add(ovChipkaart);
        List<Product> producten = new ArrayList<>();
        producten.add(product);
        ovChipkaart.setProducten(producten);
        product.setProductNummer(8);
        product.setOvChipKaarten(ovChipkaarten);
        Reiziger reizigerToSave = ReizigerUtils.generateReiziger(12);
        ovChipkaart.setReiziger(reizigerToSave);
        reizigerToSave.setOvChipkaart(ovChipkaarten);

        reizigerDao.save(reizigerToSave);

        Product newProduct = productDao.findById(8);
        Reiziger reiziger = reizigerDao.findById(12);

        assertAll(
                () -> assertNotNull(reiziger),
                () -> assertNotNull(newProduct),
                () -> assertEquals(8, reiziger.getOvChipkaart().get(0).getProducten().get(0).getProductNummer()),
                () -> assertEquals(8, newProduct.getProductNummer()),
                () -> assertEquals("naam", reiziger.getOvChipkaart().get(0).getProducten().get(0).getNaam()),
                () -> assertEquals("naam", newProduct.getNaam())
        );
    }

    @Test
    public void testUpdateReizigerWithProductShouldUpdateProduct() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(88);
        Product product = reiziger.getOvChipkaart().get(0).getProducten().get(0);

        product.setBeschrijving("beschrijving");
        product.setPrijs(new BigDecimal("20.00"));
        product.setNaam("naam");

        reizigerDao.update(reiziger);

        Product updatedProduct = productDao.findById(7);
        Reiziger updatedreiziger = reizigerDao.findById(88);

        assertAll(
                () -> assertNotNull(updatedreiziger),
                () -> assertNotNull(updatedProduct),
                () -> assertEquals(7, updatedreiziger.getOvChipkaart().get(0).getProducten().get(0).getProductNummer()),
                () -> assertEquals(7, updatedProduct.getProductNummer()),
                () -> assertEquals("naam", updatedreiziger.getOvChipkaart().get(0).getProducten().get(0).getNaam()),
                () -> assertEquals("naam", updatedProduct.getNaam()),
                () -> assertEquals("beschrijving", updatedreiziger.getOvChipkaart().get(0).getProducten().get(0).getBeschrijving()),
                () -> assertEquals("beschrijving", updatedProduct.getBeschrijving()),
                () -> assertEquals(new BigDecimal("20.00"), updatedreiziger.getOvChipkaart().get(0).getProducten().get(0).getPrijs()),
                () -> assertEquals(new BigDecimal("20.00"), updatedProduct.getPrijs())
        );
    }

    @Test
    public void testDeleteReizigerWithProductShouldNotDeleteProduct() throws SQLException {
        Reiziger reiziger = reizigerDao.findById(2);

        reizigerDao.delete(reiziger);

        Product notDeletedProduct = productDao.findById(3);
        Reiziger deletedreiziger = reizigerDao.findById(2);

        assertAll(
                () -> assertNotNull(notDeletedProduct),
                () -> assertNull(deletedreiziger)
        );
    }
}
