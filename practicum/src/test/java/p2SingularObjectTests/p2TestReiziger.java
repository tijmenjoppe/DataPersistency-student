package p2SingularObjectTests;

import domain.Reiziger;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class p2TestReiziger {

    @Test
    public void testReizigerId() {
        Reiziger reiziger = new Reiziger();
        reiziger.setReizigerId(5);
        assertEquals(5, reiziger.getReizigerId());
    }

    @Test
    public void testReizigerVoorletters() {
        Reiziger reiziger = new Reiziger();
        reiziger.setVoorletters("P.M.");
        assertEquals("P.M.", reiziger.getVoorletters());
    }

    @Test
    public void testReizigerTussenvoegsel() {
        Reiziger reiziger = new Reiziger();
        reiziger.setTussenvoegsel("de");
        assertEquals("de", reiziger.getTussenvoegsel());
    }

    @Test
    public void testReizigerAchternaam() {
        Reiziger reiziger = new Reiziger();
        reiziger.setAchternaam("Groot");
        assertEquals("Groot", reiziger.getAchternaam());
    }

    @Test
    public void testReizigerGeboortedatum() {
        Reiziger reiziger = new Reiziger();
        reiziger.setGeboortedatum(Date.valueOf("2022-01-28"));
        assertEquals(Date.valueOf("2022-01-28"), reiziger.getGeboortedatum());
    }
}
