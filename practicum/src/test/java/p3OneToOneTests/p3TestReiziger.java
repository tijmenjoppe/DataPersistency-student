package p3OneToOneTests;

import domain.Adres;
import domain.Reiziger;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class p3TestReiziger {

    @Test
    public void testReizigerAdres() {
        Reiziger reiziger = new Reiziger();
        Adres adres = new Adres();
        reiziger.setAdres(adres);
        assertEquals(adres, reiziger.getAdres());
    }
}
