-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'bedrijf'
--
-- Dit is een verkorte overname uit:
-- Lex de Haan (2004) Leerboek Oracle SQL (Derde editie, Academic Service)
--
-- Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- ------------------------------------------------------------------------

-- Creëer alle tabellen

CREATE TABLE medewerkers
(
    mnr      NUMERIC(4)     CONSTRAINT m_pk         PRIMARY KEY
                            CONSTRAINT m_mnr_chk    CHECK (mnr > 7000),
    naam     VARCHAR(12)    CONSTRAINT m_naam_nn    NOT NULL,
    voorl    VARCHAR(5)     CONSTRAINT m_voorl_nn   NOT NULL,
    functie  VARCHAR(10),
    chef     NUMERIC(4)     CONSTRAINT m_chef_fk    REFERENCES medewerkers DEFERRABLE,
    gbdatum  DATE           CONSTRAINT m_gebdat_nn  NOT NULL,
    maandsal NUMERIC(6, 2)  CONSTRAINT m_mndsal_nn  NOT NULL,
    comm     NUMERIC(6, 2),
    afd      NUMERIC(2)     DEFAULT 10
);


CREATE TABLE afdelingen
(
    anr     NUMERIC(2)  CONSTRAINT a_pk         PRIMARY KEY
                        CONSTRAINT a_anr_chk    CHECK (MOD(anr, 10) = 0),
    naam    VARCHAR(20) CONSTRAINT a_naam_nn    NOT NULL
                        CONSTRAINT a_naam_un    UNIQUE
                        CONSTRAINT a_naam_chk   CHECK (naam = UPPER(naam)),
    locatie VARCHAR(20) CONSTRAINT a_loc_nn     NOT NULL
                        CONSTRAINT a_loc_chk    CHECK (locatie = UPPER(locatie)),
    hoofd   NUMERIC(4)  CONSTRAINT a_hoofd_fk   REFERENCES medewerkers DEFERRABLE
);

ALTER TABLE medewerkers
    ADD CONSTRAINT m_afd_fk FOREIGN KEY (afd) REFERENCES afdelingen DEFERRABLE;


CREATE TABLE schalen
(
    snr        NUMERIC(2)       CONSTRAINT s_pk         PRIMARY KEY,
    ondergrens NUMERIC(6, 2)    CONSTRAINT s_onder_nn   NOT NULL
                                CONSTRAINT s_onder_chk  CHECK (ondergrens >= 0),
    bovengrens NUMERIC(6, 2)    CONSTRAINT s_boven_nn   NOT NULL,
    toelage    NUMERIC(6, 2)    CONSTRAINT s_toelg_nn   NOT NULL,
    CONSTRAINT s_ond_bov CHECK (ondergrens <= bovengrens)
);


CREATE TABLE cursussen
(
    code         VARCHAR(4)     CONSTRAINT c_pk         PRIMARY KEY,
    omschrijving VARCHAR(50)    CONSTRAINT c_omschr_nn  NOT NULL,
    type         CHAR(3)        CONSTRAINT c_type_nn    NOT NULL,
    lengte       NUMERIC(2)     CONSTRAINT c_lengte_nn  NOT NULL,
    CONSTRAINT c_code_chk CHECK (code = UPPER(code)),
    CONSTRAINT c_type_chk CHECK (type IN ('ALG', 'BLD', 'DSG'))
);


CREATE TABLE uitvoeringen
(
    cursus     VARCHAR(4)   CONSTRAINT u_cursus_nn  NOT NULL
                            CONSTRAINT u_cursus_fk  REFERENCES cursussen DEFERRABLE,
    begindatum DATE         CONSTRAINT u_begin_nn   NOT NULL,
    docent     NUMERIC(4)   CONSTRAINT u_docent_fk  REFERENCES medewerkers DEFERRABLE,
    locatie    VARCHAR(20),
    CONSTRAINT u_pk PRIMARY KEY (cursus, begindatum)
);


CREATE TABLE inschrijvingen
(
    cursist    NUMERIC(4)   CONSTRAINT i_cursist_nn NOT NULL
                            CONSTRAINT i_cursist_fk REFERENCES medewerkers DEFERRABLE,
    cursus     VARCHAR(4)   CONSTRAINT i_cursus_nn  NOT NULL,
    begindatum DATE         CONSTRAINT i_begin_nn   NOT NULL,
    evaluatie  NUMERIC(1)   CONSTRAINT i_eval_chk   CHECK (evaluatie IN (1, 2, 3, 4, 5)),
    CONSTRAINT i_pk         PRIMARY KEY (cursist, cursus, begindatum),
    CONSTRAINT i_uitv_fk    FOREIGN KEY (cursus, begindatum) REFERENCES uitvoeringen DEFERRABLE
);


CREATE TABLE historie
(
    mnr         NUMERIC(4)      CONSTRAINT h_mnr_nn     NOT NULL
                                CONSTRAINT h_mnr_fk     REFERENCES medewerkers ON DELETE CASCADE DEFERRABLE,
    beginjaar   NUMERIC(4)      CONSTRAINT h_bjaar_nn   NOT NULL,
    begindatum  DATE            CONSTRAINT h_begin_nn   NOT NULL,
    einddatum   DATE,
    afd         NUMERIC(2)      CONSTRAINT h_afd_nn     NOT NULL
                                CONSTRAINT h_afd_fk     REFERENCES afdelingen DEFERRABLE,
    maandsal    NUMERIC(6, 2)   CONSTRAINT h_sal_nn     NOT NULL,
    opmerkingen VARCHAR(60),
    CONSTRAINT h_pk           PRIMARY KEY (mnr, begindatum),
    CONSTRAINT h_beg_eind_chk CHECK (begindatum < einddatum)
);
