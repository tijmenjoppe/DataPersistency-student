package p3OneToOneTests;

import domain.Adres;
import domain.Reiziger;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class p3TestAdres {

    @Test
    void testAdresId() {
        Adres adres = new Adres();
        adres.setAdresId(1);

        assertEquals(1, adres.getAdresId());
    }

    @Test
    void testPostcode() {
        Adres adres = new Adres();
        adres.setPostcode("1111aa");

        assertEquals("1111aa", adres.getPostcode());
    }

    @Test
    void testHuisnummer() {
        Adres adres = new Adres();
        adres.setHuisnummer("13b");

        assertEquals("13b", adres.getHuisnummer());
    }

    @Test
    void testStraat() {
        Adres adres = new Adres();
        adres.setStraat("Fabeltjeskrant");

        assertEquals("Fabeltjeskrant", adres.getStraat());
    }

    @Test
    void testWoonplaats() {
        Adres adres = new Adres();
        adres.setWoonplaats("Utrecht");

        assertEquals("Utrecht", adres.getWoonplaats());
    }
    @Test
    void testReiziger() {
        Adres adres = new Adres();
        Reiziger reiziger = new Reiziger();
        adres.setReiziger(reiziger);

        assertEquals(reiziger, adres.getReiziger());
    }
}
