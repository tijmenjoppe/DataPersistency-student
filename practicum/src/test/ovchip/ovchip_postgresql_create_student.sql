-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'ov-chipkaart'
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- ------------------------------------------------------------------------

-- Creëer tabellen, primaire sleutels en foreign keys

CREATE TABLE reiziger
(
    reiziger_id   NUMERIC(10)  NOT NULL,
    voorletters   VARCHAR(10)  NOT NULL,
    tussenvoegsel VARCHAR(10),
    achternaam    VARCHAR(255) NOT NULL,
    geboortedatum DATE,
    PRIMARY KEY (reiziger_id)
);

CREATE TABLE adres
(
    adres_id    NUMERIC(10)  NOT NULL,
    postcode    VARCHAR(10)  NOT NULL,
    huisnummer  VARCHAR(10)  NOT NULL,
    straat      VARCHAR(255) NOT NULL,
    woonplaats  VARCHAR(255) NOT NULL,
    reiziger_id NUMERIC(10)  NOT NULL REFERENCES reiziger (reiziger_id),
    PRIMARY KEY (adres_id),
    UNIQUE (reiziger_id)
);

ALTER TABLE adres
    ADD CONSTRAINT adres_reiziger_unique UNIQUE (reiziger_id);

CREATE TABLE ov_chipkaart
(
    kaart_nummer NUMERIC(10)    NOT NULL,
    geldig_tot   DATE           NOT NULL,
    klasse       NUMERIC(1)     NOT NULL,
    saldo        NUMERIC(16, 2) NOT NULL,
    reiziger_id  NUMERIC(10)    NOT NULL REFERENCES reiziger (reiziger_id),
    PRIMARY KEY (kaart_nummer)
);

CREATE TABLE product
(
    product_nummer NUMERIC(10)    NOT NULL,
    naam           VARCHAR(30)    NOT NULL,
    beschrijving   VARCHAR(512)   NOT NULL,
    prijs          NUMERIC(16, 2) NOT NULL,
    PRIMARY KEY (product_nummer)
);


CREATE TABLE ov_chipkaart_product
(
    kaart_nummer   NUMERIC(10) NOT NULL REFERENCES ov_chipkaart (kaart_nummer),
    product_nummer NUMERIC(10) NOT NULL REFERENCES product (product_nummer),
    status         VARCHAR(10),
    last_update    DATE,
    PRIMARY KEY (kaart_nummer, product_nummer)
);
