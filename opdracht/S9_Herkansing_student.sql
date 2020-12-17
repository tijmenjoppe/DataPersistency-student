-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S9: Aanvullende herkansingsopdracht
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
--
--
-- Opdracht: schrijf SQL-queries om onderstaande resultaten op te vragen,
-- aan te maken, verwijderen of aan te passen in de database van de
-- bedrijfscasus.
--
-- Codeer je uitwerking onder de regel 'DROP VIEW ...' (bij een SELECT)
-- of boven de regel 'ON CONFLICT DO NOTHING;' (bij een INSERT)
-- Je kunt deze eigen query selecteren en los uitvoeren, en wijzigen tot
-- je tevreden bent.
--
-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen.
-- ------------------------------------------------------------------------


-- S9.1  Overstap
--
-- Jan-Jaap den Draaier is per 1 oktober 2020 manager van personeelszaken.
-- Hij komt direct onder de directeur te vallen en gaat 2100 euro per
-- maand verdienen.
-- Voer alle queries uit om deze wijziging door te voeren.
INSERT
ON CONFLICT DO NOTHING;                                                                                         -- [TEST]


-- S9.2  Beginjaar
--
-- Voeg een beperkingsregel `h_beginjaar_chk` toe aan de historietabel
-- die controleert of een ingevoerde waarde in de kolom `beginjaar` een
-- correcte waarde heeft, met andere woorden: dat het om het huidige jaar
-- gaat of een jaar dat in het verleden ligt.
-- Test je beperkingsregel daarna met een INSERT die deze regel schendt.


-- S9.3  Opmerkingen
--
-- Geef uit de historietabel alle niet-lege opmerkingen bij de huidige posities
-- van medewerkers binnen het bedrijf. Geef ter referentie ook het medewerkersnummer
-- bij de resultaten.
-- DROP VIEW IF EXISTS s9_3; CREATE OR REPLACE VIEW s9_3 AS                                                     -- [TEST]


-- S9.4  Carrièrepad
--
-- Toon van alle medewerkers die nú op het hoofdkantoor werken hun historie
-- binnen het bedrijf: geef van elke positie die ze bekleed hebben de
-- de naam van de medewerker, de begindatum, de naam van hun afdeling op dat
-- moment (`afdeling`) en hun toenmalige salarisschaal (`schaal`).
-- Sorteer eerst op naam en dan op ingangsdatum.
-- DROP VIEW IF EXISTS s9_4; CREATE OR REPLACE VIEW s9_4 AS                                                     -- [TEST]


-- S9.5 Aanloop
--
-- Toon voor elke medewerker de naam en hoelang zij in andere functies hebben
-- gewerkt voordat zij op hun huidige positie kwamen (`tijdsduur`).
-- Rond naar beneden af op gehele jaren.
-- DROP VIEW IF EXISTS s9_5; CREATE OR REPLACE VIEW s9_5 AS                                                     -- [TEST]


-- S9.6 Index
--
-- Maak een index `historie_afd_idx` op afdelingsnummer in de historietabel.



-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_exists('S9.1', 1) AS resultaat
UNION
SELECT 'S9.2 wordt niet getest: zelf handmatig testen.' AS resultaat
UNION
SELECT * FROM test_select('S9.3') AS resultaat
UNION
SELECT * FROM test_select('S9.4') AS resultaat
UNION
SELECT * FROM test_select('S9.5') AS resultaat
UNION
SELECT 'S9.6 wordt niet getest: geen test mogelijk.' AS resultaat
ORDER BY resultaat;
