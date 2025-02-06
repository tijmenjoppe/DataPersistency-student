package p4OneToManyTests;

import domain.OvChipkaart;
import domain.Reiziger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class p4TestReiziger {

    @Test
    public void testReizigerOvChipkaart() {
        Reiziger reiziger = new Reiziger();
        List<OvChipkaart> ovChipkaartList = new ArrayList<>();
        reiziger.setOvChipkaart(ovChipkaartList);
        assertEquals(ovChipkaartList, reiziger.getOvChipkaart());
    }
}
