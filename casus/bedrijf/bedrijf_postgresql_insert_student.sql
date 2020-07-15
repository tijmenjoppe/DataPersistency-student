-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'bedrijf'
--
-- Dit is een verkorte overname uit:
-- Lex de Haan (2004) Leerboek Oracle SQL (Derde editie, Academic Service)
--
-- Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------
SET DATESTYLE = 'DMY'; -- voor het date format in de inserts

-- Vul de tabellen

-- Tabel `medewerkers` en `afdelingen` samen in één transactie vanwege cyclische referentie

TRUNCATE TABLE medewerkers CASCADE;
TRUNCATE TABLE afdelingen CASCADE;

START TRANSACTION;

SET CONSTRAINTS m_afd_fk DEFERRED; -- Foreign key constraints uitzetten

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm, afd)
VALUES (7369, 'SMIT', 'N', 'TRAINER', 7902, '17-12-1985', 800, NULL, 20),
       (7499, 'ALDERS', 'JAM', 'VERKOPER', 7698, '20-02-1981', 1600, 200, 30),
       (7521, 'DE WAARD', 'TF', 'VERKOPER', 7698, '22-02-1982', 1250, 500, 30),
       (7566, 'JANSEN', 'JM', 'MANAGER', 7839, '02-04-1987', 2975, NULL, 20),
       (7654, 'MARTENS', 'P', 'VERKOPER', 7698, '28-09-1976', 1250, 1400, 30),
       (7698, 'BLAAK', 'R', 'MANAGER', 7839, '01-11-1983', 2850, NULL, 30),
       (7782, 'CLERCKX', 'AB', 'MANAGER', 7839, '09-06-1985', 2450, NULL, 10),
       (7788, 'SCHOTTEN', 'SCJ', 'TRAINER', 7566, '26-11-1979', 3000, NULL, 20),
       (7839, 'DE KONING', 'CC', 'DIRECTEUR', NULL, '17-11-1972', 5000, NULL, 10),
       (7844, 'DEN DRAAIER', 'JJ', 'VERKOPER', 7698, '28-09-1988', 1500, 0, 30),
       (7876, 'ADAMS', 'AA', 'TRAINER', 7788, '30-12-1986', 1100, NULL, 20),
       (7900, 'JANSEN', 'R', 'BOEKHOUDER', 7698, '03-12-1989', 800, NULL, 30),
       (7902, 'SPIJKER', 'MG', 'TRAINER', 7566, '13-02-1979', 3000, NULL, 20),
       (7934, 'MOLENAAR', 'TJA', 'BOEKHOUDER', 7782, '23-01-1982', 1300, NULL, 10);

INSERT INTO afdelingen (anr, naam, locatie, hoofd)
VALUES (10, 'HOOFDKANTOOR', 'LEIDEN', 7782),
       (20, 'OPLEIDINGEN', 'DE MEERN', 7566),
       (30, 'VERKOOP', 'UTRECHT', 7698),
       (40, 'PERSONEELSZAKEN', 'GRONINGEN', 7839);

COMMIT;


TRUNCATE TABLE schalen CASCADE;

INSERT INTO schalen (snr, ondergrens, bovengrens, toelage)
VALUES (1, 700, 1200, 0),
       (2, 1201, 1400, 50),
       (3, 1401, 2000, 100),
       (4, 2001, 3000, 200),
       (5, 3001, 9999, 500);


TRUNCATE TABLE cursussen CASCADE;

INSERT INTO cursussen (code, omschrijving, type, lengte)
VALUES ('S02', 'Introductiecursus SQL', 'ALG', 4),
       ('OAG', 'Oracle voor applicatiegebruikers', 'ALG', 1),
       ('JAV', 'Java voor Oracle ontwikkelaars', 'BLD', 4),
       ('PLS', 'Introductie PL/SQL', 'BLD', 1),
       ('XML', 'XML voor Oracle ontwikkelaars', 'BLD', 2),
       ('ERM', 'Datamodellering met ERM', 'DSG', 3),
       ('PMT', 'Procesmodelleringstechnieken', 'DSG', 1),
       ('RSO', 'Relationeel systeemontwerp', 'DSG', 2),
       ('PRO', 'Prototyping', 'DSG', 5),
       ('GEN', 'Systeemgeneratie', 'DSG', 4);


TRUNCATE TABLE uitvoeringen CASCADE;

INSERT INTO uitvoeringen (cursus, begindatum, docent, locatie)
VALUES ('S02', '12-04-2019', 7902, 'DE MEERN'),
       ('OAG', '10-08-2019', 7566, 'UTRECHT'),
       ('S02', '04-10-2019', 7369, 'MAASTRICHT'),
       ('S02', '13-12-2019', 7369, 'DE MEERN'),
       ('JAV', '13-12-2019', 7566, 'MAASTRICHT'),
       ('XML', '03-02-2020', 7369, 'DE MEERN'),
       ('JAV', '01-02-2020', 7876, 'DE MEERN'),
       ('PLS', '11-09-2020', 7788, 'DE MEERN'),
       ('XML', '18-09-2020', NULL, 'MAASTRICHT'),
       ('OAG', '27-09-2020', 7902, 'DE MEERN');

-- Toekomstige uitvoeringen
INSERT INTO uitvoeringen (cursus, begindatum, docent, locatie)
VALUES ('ERM', '15-01-2021', NULL, NULL),
       ('PRO', '19-02-2021', NULL, 'DE MEERN'),
       ('RSO', '24-02-2021', 7788, 'UTRECHT');


TRUNCATE TABLE inschrijvingen CASCADE;

INSERT INTO inschrijvingen (cursist, cursus, begindatum, evaluatie)
VALUES (7499, 'S02', '12-04-2019', 4),
       (7934, 'S02', '12-04-2019', 5),
       (7698, 'S02', '12-04-2019', 4),
       (7876, 'S02', '12-04-2019', 2),
       (7788, 'S02', '04-10-2019', NULL),
       (7839, 'S02', '04-10-2019', 3),
       (7902, 'S02', '04-10-2019', 4),
       (7902, 'S02', '13-12-2019', NULL),
       (7698, 'S02', '13-12-2019', NULL),
       (7521, 'OAG', '10-08-2019', 4),
       (7900, 'OAG', '10-08-2019', 4),
       (7902, 'OAG', '10-08-2019', 5),
       (7844, 'OAG', '27-09-2020', 5),
       (7499, 'JAV', '13-12-2019', 2),
       (7782, 'JAV', '13-12-2019', 5),
       (7876, 'JAV', '13-12-2019', 5),
       (7788, 'JAV', '13-12-2019', 5),
       (7839, 'JAV', '13-12-2019', 4),
       (7566, 'JAV', '01-02-2020', 3),
       (7788, 'JAV', '01-02-2020', 4),
       (7698, 'JAV', '01-02-2020', 5),
       (7900, 'XML', '03-02-2020', 4),
       (7499, 'XML', '03-02-2020', 5),
       (7566, 'PLS', '11-09-2020', NULL),
       (7499, 'PLS', '11-09-2020', NULL),
       (7876, 'PLS', '11-09-2020', NULL);


-- Historie van personeelszaken: één blok per medewerker
TRUNCATE TABLE historie CASCADE;

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7369, 2020, '01-01-2020', '01-02-2020', 40, 950, ''),
       (7369, 2020, '01-02-2020', NULL, 20, 800, 'Overgang naar opleidingen, met salaris"correctie" 150');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7499, 2008, '01-06-2008', '01-07-2009', 30, 1000, ''),
       (7499, 2009, '01-07-2009', '01-12-2013', 30, 1300, ''),
       (7499, 2013, '01-12-2013', '01-10-2015', 30, 1500, ''),
       (7499, 2015, '01-10-2015', '01-11-2019', 30, 1700, ''),
       (7499, 2019, '01-11-2019', NULL, 30, 1600, 'Targets al weer niet gehaald; salarisverlaging');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7521, 2006, '01-10-2006', '01-08-2007', 20, 1000, ''),
       (7521, 2007, '01-08-2007', '01-01-2009', 30, 1000, 'Overgang naar afdeling verkoop op eigen verzoek'),
       (7521, 2009, '01-01-2009', '15-12-2012', 30, 1150, ''),
       (7521, 2012, '15-12-2012', '01-10-2014', 30, 1250, ''),
       (7521, 2014, '01-10-2014', '01-10-2017', 20, 1250, ''),
       (7521, 2017, '01-10-2017', '01-02-2020', 30, 1300, ''),
       (7521, 2020, '01-02-2020', NULL, 30, 1250, '');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7566, 2002, '01-01-2002', '01-12-2002', 20, 900, ''),
       (7566, 2002, '01-12-2002', '15-08-2004', 20, 950, ''),
       (7566, 2004, '15-08-2004', '01-01-2006', 30, 1000, 'Niet zo geschikt als docent; dan maar naar verkoop!'),
       (7566, 2006, '01-01-2006', '01-07-2006', 30, 1175, 'Verkoop is ook al niet zo''n succes...'),
       (7566, 2006, '01-07-2006', '15-03-2007', 10, 1175, ''),
       (7566, 2007, '15-03-2007', '01-04-2007', 10, 2200, ''),
       (7566, 2007, '01-04-2007', '01-06-2009', 10, 2300, ''),
       (7566, 2009, '01-06-2009', '01-07-2012', 40, 2300, 'Van hoofdkantoor naar personeelszaken; salaris blijft 2300'),
       (7566, 2012, '01-07-2012', '01-11-2012', 40, 2450, ''),
       (7566, 2012, '01-11-2012', '01-09-2014', 20, 2600, 'Terug naar afdeling opleidingen, als hoofd'),
       (7566, 2014, '01-09-2014', '01-03-2015', 20, 2550, ''),
       (7566, 2015, '01-03-2015', '15-10-2019', 20, 2750, ''),
       (7566, 2019, '15-10-2019', NULL, 20, 2975, '');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7654, 2019, '01-01-2019', '15-10-2019', 30, 1100, 'Senior verkoper; zou wel eens een aanwinst kunnen zijn?'),
       (7654, 2019, '15-10-2019', NULL, 30, 1250, 'Valt toch een beetje tegen.');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7698, 2002, '01-06-2002', '01-01-2003', 30, 900, ''),
       (7698, 2003, '01-01-2003', '01-01-2004', 30, 1275, ''),
       (7698, 2004, '01-01-2004', '15-04-2005', 30, 1500, ''),
       (7698, 2005, '15-04-2005', '01-01-2006', 30, 2100, ''),
       (7698, 2006, '01-01-2006', '15-10-2009', 30, 2200, ''),
       (7698, 2009, '15-10-2009', NULL, 30, 2850, 'Gepromoveerd tot hoofd van de afdeling verkoop');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7782, 2008, '01-07-2008', NULL, 10, 2450, 'Aangenomen als manager voor het hoofdkantoor');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7788, 2002, '01-07-2002', '01-01-2003', 20, 900, ''),
       (7788, 2003, '01-01-2003', '15-04-2005', 20, 950, ''),
       (7788, 2005, '15-04-2005', '01-06-2005', 40, 950, 'Overgang naar personeelszaken, zonder salarisverhoging'),
       (7788, 2005, '01-06-2005', '15-04-2006', 40, 1100, ''),
       (7788, 2006, '15-04-2006', '01-05-2006', 20, 1100, ''),
       (7788, 2006, '01-05-2006', '15-02-2007', 20, 1800, ''),
       (7788, 2007, '15-02-2007', '01-12-2009', 20, 1250, 'Salarisverlaging met 550, vanwege onvoldoende prestaties'),
       (7788, 2009, '01-12-2009', '15-10-2012', 20, 1350, ''),
       (7788, 2012, '15-10-2012', '01-01-2018', 20, 1400, ''),
       (7788, 2018, '01-01-2018', '01-01-2019', 20, 1700, ''),
       (7788, 2019, '01-01-2019', '01-07-2019', 20, 1800, ''),
       (7788, 2019, '01-07-2019', '01-06-2020', 20, 1800, ''),
       (7788, 2020, '01-06-2020', NULL, 20, 3000, '');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7839, 2002, '01-01-2002', '01-08-2002', 30, 1000, 'Oprichter en eerste werknemer van het bedrijf'),
       (7839, 2002, '01-08-2002', '15-05-2004', 30, 1200, ''),
       (7839, 2004, '15-05-2004', '01-01-2005', 30, 1500, ''),
       (7839, 2005, '01-01-2005', '01-07-2005', 30, 1750, ''),
       (7839, 2005, '01-07-2005', '01-11-2005', 10, 2000, 'Hoofdkantoor als nieuwe zelfstandige afdeling opgericht'),
       (7839, 2005, '01-11-2005', '01-02-2006', 10, 2200, ''),
       (7839, 2006, '01-02-2006', '15-06-2009', 10, 2500, ''),
       (7839, 2009, '15-06-2009', '01-12-2013', 10, 2900, ''),
       (7839, 2013, '01-12-2013', '01-09-2015', 10, 3400, ''),
       (7839, 2015, '01-09-2015', '01-10-2017', 10, 4200, ''),
       (7839, 2017, '01-10-2017', '01-10-2018', 10, 4500, ''),
       (7839, 2018, '01-10-2018', '01-11-2019', 10, 4800, ''),
       (7839, 2019, '01-11-2019', '15-02-2020', 10, 4900, ''),
       (7839, 2020, '15-02-2020', NULL, 10, 5000, '');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7844, 2015, '01-05-2015', '01-01-2017', 30, 900, ''),
       (7844, 2018, '15-10-2018', '01-11-2018', 10, 1200, 'Project (van een halve maand) voor het hoofdkantoor'),
       (7844, 2018, '01-11-2018', '01-01-2020', 30, 1400, ''),
       (7844, 2020, '01-01-2020', NULL, 30, 1500, '');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7876, 2020, '01-01-2020', '01-02-2020', 20, 950, ''),
       (7876, 2020, '01-02-2020', NULL, 20, 1100, '');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7900, 2020, '01-07-2020', NULL, 30, 800, 'Junior verkoper -- moet nog veel leren...');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7902, 2018, '01-09-2018', '01-10-2018', 40, 1400, ''),
       (7902, 2018, '01-10-2018', '15-03-2019', 30, 1650, ''),
       (7902, 2019, '15-03-2019', '01-01-2020', 30, 2500, ''),
       (7902, 2020, '01-01-2020', '01-08-2020', 30, 3000, ''),
       (7902, 2020, '01-08-2020', NULL, 20, 3000, '');

INSERT INTO historie (mnr, beginjaar, begindatum, einddatum, afd, maandsal, opmerkingen)
VALUES (7934, 2018, '01-02-2018', '01-05-2018', 10, 1275, ''),
       (7934, 2018, '01-05-2018', '01-02-2019', 10, 1280, ''),
       (7934, 2019, '01-02-2019', '01-01-2020', 10, 1290, ''),
       (7934, 2020, '01-01-2020', NULL, 10, 1300, '');


-- Verzamel OBJECT-STATISTICS voor de optimizer
-- EXECUTE dbms_stats.gather_schema_stats(ownname => user, cascade => TRUE);
