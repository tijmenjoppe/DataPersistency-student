package p5ManyToManyTests;

import domain.OvChipkaart;
import domain.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class p5TestOvChipkaart {

    @Test
    public void testOvChipkaartProduct() {
        OvChipkaart ovChipkaart = new OvChipkaart();
        List<Product> products = new ArrayList<>();
        ovChipkaart.setProducten(products);
        assertEquals(products, ovChipkaart.getProducten());
    }
}
