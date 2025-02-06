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
import util.SetupDatabase;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class p5hTestProductDao {
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
    public void testProductDaoInitialState() throws SQLException {
        List<Product> producten = productDao.findAll();
        Product productDalVoordeel = producten.stream().filter(x -> x.getProductNummer() == 3).findAny().get();

        assertAll(
                () -> assertTrue(producten.size() > 1),
                () -> assertEquals(3, productDalVoordeel.getProductNummer()),
                () -> assertEquals(new BigDecimal("50.00"), productDalVoordeel.getPrijs()),
                () -> assertEquals("Dal Voordeel 40%", productDalVoordeel.getNaam()),
                () -> assertEquals("40% korting buiten de spits en in het weekeind.", productDalVoordeel.getBeschrijving()),
                () -> assertEquals(3, productDalVoordeel.getOvChipKaarten().size()),
                () -> assertEquals(35283, productDalVoordeel.getOvChipKaarten().stream().filter(x -> x.getKaartNummer() == 35283).findAny().get().getKaartNummer()),
                () -> assertEquals(46392, productDalVoordeel.getOvChipKaarten().stream().filter(x -> x.getKaartNummer() == 46392).findAny().get().getKaartNummer()),
                () -> assertEquals(90537, productDalVoordeel.getOvChipKaarten().stream().filter(x -> x.getKaartNummer() == 90537).findAny().get().getKaartNummer())
        );
    }

    @Test
    public void testProductDaoFind() throws SQLException {
        Product product = productDao.findById(4);

        assertAll(
                () -> assertEquals(4, product.getProductNummer()),
                () -> assertEquals(new BigDecimal("26.00"), product.getPrijs()),
                () -> assertEquals("Amsterdam Travel Ticket", product.getNaam()),
                () -> assertEquals("Onbeperkt reizen door Amsterdam.", product.getBeschrijving())
        );
    }

    @Test
    public void testProductDaoFindByOvChipkaart() throws SQLException {
        OvChipkaart ovChipkaart = ovChipkaartDao.findById(90537);
        List<Product> producten = productDao.findByOvChipkaart(ovChipkaart);

        Product productDagkaartFiets = producten.stream().filter(x -> x.getProductNummer() == 2).findAny().get();
        Product productDalVoordeel = producten.stream().filter(x -> x.getProductNummer() == 3).findAny().get();

        assertAll(
                () -> assertEquals(3, productDalVoordeel.getProductNummer()),
                () -> assertEquals(new BigDecimal("50.00"), productDalVoordeel.getPrijs()),
                () -> assertEquals("Dal Voordeel 40%", productDalVoordeel.getNaam()),
                () -> assertEquals("40% korting buiten de spits en in het weekeind.", productDalVoordeel.getBeschrijving()),
                () -> assertEquals(2, productDagkaartFiets.getProductNummer()),
                () -> assertEquals(new BigDecimal("6.20"), productDagkaartFiets.getPrijs()),
                () -> assertEquals("Dagkaart fiets", productDagkaartFiets.getNaam()),
                () -> assertEquals("Uw fiets mee in de trein, 1 dag geldig in Nederland.", productDagkaartFiets.getBeschrijving())
        );
    }

    @Test
    public void testProductDoesntExist() throws SQLException {
        Product product = productDao.findById(32190);
        assertNull(product);
    }

    @Test
    public void testUpdateAttachedProduct() throws SQLException {
        Product productDagkaart2eklas = productDao.findById(1);

        assertAll(
                () -> assertEquals(1, productDagkaart2eklas.getProductNummer()),
                () -> assertEquals(new BigDecimal("50.60"), productDagkaart2eklas.getPrijs()),
                () -> assertEquals("Dagkaart 2e klas", productDagkaart2eklas.getNaam()),
                () -> assertEquals("Een hele dag onbeperkt reizen met de trein.", productDagkaart2eklas.getBeschrijving())
        );

        productDagkaart2eklas.setPrijs(new BigDecimal("20.00"));
        productDagkaart2eklas.setNaam("Dagkaart tweede klas");
        productDagkaart2eklas.setBeschrijving("Een dag lang onbeperkt reizen met de trein.");

        productDao.update(productDagkaart2eklas);

        Product updatedProduct = productDao.findById(1);

        assertAll(
                () -> assertEquals(1, updatedProduct.getProductNummer()),
                () -> assertEquals(new BigDecimal("20.00"), updatedProduct.getPrijs()),
                () -> assertEquals("Dagkaart tweede klas", updatedProduct.getNaam()),
                () -> assertEquals("Een dag lang onbeperkt reizen met de trein.", updatedProduct.getBeschrijving())
        );
    }

    @Test
    public void testDeleteDoesNotDeleteOvChipkaartNorReiziger() throws SQLException {
        Product product = productDao.findById(7);
        assertAll(
                () -> assertEquals(1, product.getOvChipKaarten().size()),
                () -> assertEquals(81267, product.getOvChipKaarten().get(0).getKaartNummer()),
                () -> assertEquals(88, product.getOvChipKaarten().get(0).getReiziger().getReizigerId())
        );


        productDao.delete(product);

        assertAll(
                () -> assertNull(productDao.findById(7)),
                () -> assertNotNull(ovChipkaartDao.findById(81267)),
                () -> assertNotNull(reizigerDao.findById(88))
            );
    }
}
