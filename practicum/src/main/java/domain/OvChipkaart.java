package domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OvChipkaart {

    public int getKaartNummer() {
        return 0;
    }

    public void setKaartNummer(int kaartNummer) {

    }

    public Date getGeldigTot() {
        return null;
    }

    public void setGeldigTot(Date geldigTot) {

    }

    public BigInteger getKlasse() {
        return null;
    }

    public void setKlasse(BigInteger klasse) {

    }

    public BigDecimal getSaldo() {
        return null;
    }

    public void setSaldo(BigDecimal saldo) {

    }

    public Reiziger getReiziger() {
        return null;
    }

    public void setReiziger(Reiziger reiziger) {
    }

    public List<Product> getProducten() {
        return null;
    }

    public void setProducten(List<Product> producten) {

    }
}
