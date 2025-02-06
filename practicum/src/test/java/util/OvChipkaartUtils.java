package util;

import domain.Adres;
import domain.OvChipkaart;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertAll;

public class OvChipkaartUtils {
    static Random random = new Random();

    public static OvChipkaart generateOvChipkaart(int id) {
        OvChipkaart ovChipkaart = new OvChipkaart();

        ovChipkaart.setGeldigTot(getRandomGeldigTot());
        ovChipkaart.setSaldo(getRandomSaldo());
        ovChipkaart.setKlasse(getRandomKlasse());
        ovChipkaart.setKaartNummer(id);

        return ovChipkaart;
    }


    private static Date getRandomGeldigTot() {
        int randomYear = random.nextInt(11) + 2020;
        int randomMonth = random.nextInt(12) + 1;
        int randomDay = random.nextInt(26) + 1;

        return Date.valueOf(String.format("%d-%d-%d", randomYear, randomMonth, randomDay));
    }

    private static BigDecimal getRandomSaldo() {
        return new BigDecimal(random.nextInt(60) - 10).setScale(2, RoundingMode.CEILING);
    }

    private static BigInteger getRandomKlasse() {
        if (random.nextBoolean()) {
            return BigInteger.ONE;
        }
        return BigInteger.TWO;
    }

    public static void assertEquals(OvChipkaart expected, OvChipkaart actual) {
        assertAll(
                () -> Assertions.assertEquals(expected.getKaartNummer(), actual.getKaartNummer()),
                () -> Assertions.assertEquals(expected.getGeldigTot(), actual.getGeldigTot()),
                () -> Assertions.assertEquals(expected.getKlasse(), actual.getKlasse()),
                () -> Assertions.assertEquals(expected.getSaldo(), actual.getSaldo())
        );
    }
}
