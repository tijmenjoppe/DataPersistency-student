package p5ManyToManyTests;

import domain.OvChipkaart;
import domain.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class p5TestProduct {
    @Test
    void testProductNummer() {
        Product product = new Product();
        product.setProductNummer(4);

        assertEquals(4, product.getProductNummer());
    }
    @Test
    void testProductNaam() {
        Product product = new Product();
        product.setNaam("Naam");

        assertEquals("Naam", product.getNaam());
    }
    @Test
    void testProductBeschrijving() {
        Product product = new Product();
        product.setBeschrijving("Beschrijving");

        assertEquals("Beschrijving", product.getBeschrijving());
    }
    @Test
    void testOvChipkaarten() {
        Product product = new Product();
        List<OvChipkaart> ovChipkaarten = new ArrayList<>();
        product.setOvChipKaarten(ovChipkaarten);

        assertEquals(ovChipkaarten, product.getOvChipKaarten());
    }
    @Test
    void testOvPrijs() {
        Product product = new Product();
        product.setPrijs(new BigDecimal(10));

        assertEquals(new BigDecimal(10), product.getPrijs());
    }
}
