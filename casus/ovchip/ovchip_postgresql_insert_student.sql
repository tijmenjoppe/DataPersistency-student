-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'ov-chipkaart'
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- ------------------------------------------------------------------------

-- Vul alle tabellen met minimaal 5 records

INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum)
VALUES (1, 'G', 'van', 'Rijn', TO_DATE('17-09-2002', 'dd-mm-yyyy')),
       (2, 'B', 'van', 'Rijn', TO_DATE('22-10-2002', 'dd-mm-yyyy')),
       (3, 'H', NULL, 'Lubben', TO_DATE('11-08-1998', 'dd-mm-yyyy')),
       (4, 'F', NULL, 'Memari', TO_DATE('03-12-2002', 'dd-mm-yyyy')),
       (5, 'G', NULL, 'Piccardo', TO_DATE('03-12-2002', 'dd-mm-yyyy'));

INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)
VALUES (35283, TO_DATE('31-05-2018', 'dd-mm-yyyy'), 2, 25.50, 2),
       (46392, TO_DATE('31-05-2017', 'dd-mm-yyyy'), 2, 5.50, 2),
       (57401, TO_DATE('31-05-2015', 'dd-mm-yyyy'), 2, 0.0, 2),
       (68514, TO_DATE('31-03-2020', 'dd-mm-yyyy'), 1, 2.50, 3),
       (79625, TO_DATE('31-01-2020', 'dd-mm-yyyy'), 1, 25.50, 4),
       (90537, TO_DATE('31-12-2019', 'dd-mm-yyyy'), 2, 20.0, 5),
       (18326, TO_DATE('31-12-2017', 'dd-mm-yyyy'), 2, 0.0, 5);

INSERT INTO adres (adres_id, postcode, straat, huisnummer, woonplaats, reiziger_id)
VALUES (1, '3511LX', 'Visschersplein', '37', 'Utrecht', 1),
       (2, '3521AL', 'Jaarbeursplein', '6A', 'Utrecht', 2),
       (3, '6707AA', 'Stadsbrink', '375', 'Wageningen', 3),
       (4, '3817CH', 'Arnhemseweg', '4', 'Amersfoort', 4),
       (5, '3572WP', 'Vermeulenstraat', '22', 'Utrecht', 5);

INSERT INTO product (product_nummer, naam, beschrijving, prijs)
VALUES (1, 'Dagkaart 2e klas', 'Een hele dag onbeperkt reizen met de trein.', 50.60),
       (2, 'Dagkaart fiets', 'Uw fiets mee in de trein, 1 dag geldig in Nederland.', 6.20),
       (3, 'Dal Voordeel 40%', '40% korting buiten de spits en in het weekeind.', 50.0),
       (4, 'Amsterdam Travel Ticket', 'Onbeperkt reizen door Amsterdam.', 26.0),
       (5, 'Railrunner', 'Voordelig reizen voor kinderen.', 2.50),
       (6, 'Studentenreisproduct', 'Gratis of met korting reizen als je studeert.', 0.0);

INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update)
VALUES (35283, 3, 'actief', TO_DATE('31-05-2017', 'dd-mm-yyyy')),
       (35283, 1, 'gekocht', TO_DATE('05-04-2018', 'dd-mm-yyyy')),
       (35283, 2, 'gekocht', TO_DATE('05-04-2018', 'dd-mm-yyyy')),
       (46392, 3, 'verlopen', TO_DATE('31-05-2017', 'dd-mm-yyyy')),
       (68514, 6, 'actief', TO_DATE('01-04-2018', 'dd-mm-yyyy')),
       (79625, 6, 'actief', TO_DATE('01-02-2018', 'dd-mm-yyyy')),
       (90537, 3, 'actief', TO_DATE('01-02-2018', 'dd-mm-yyyy')),
       (90537, 2, 'gekocht', TO_DATE('14-04-2018', 'dd-mm-yyyy'));
