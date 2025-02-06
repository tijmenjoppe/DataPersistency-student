package domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Product {

    public int getProductNummer() {
        return 0;
    }

    public void setProductNummer(int productNummer) {
    }

    public String getNaam() {
        return null;
    }

    public void setNaam(String naam) {

    }

    public String getBeschrijving() {
        return null;
    }

    public void setBeschrijving(String beschrijving) {

    }

    public BigDecimal getPrijs() {
        return null;
    }

    public void setPrijs(BigDecimal prijs) {

    }

    public List<OvChipkaart> getOvChipKaarten() {
        return null;
    }

    public void setOvChipKaarten(List<OvChipkaart> ovChipKaarten) {

    }
}
