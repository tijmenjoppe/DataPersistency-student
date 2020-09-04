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

DROP TABLE IF EXISTS medewerkers      CASCADE;
DROP TABLE IF EXISTS afdelingen       CASCADE;
DROP TABLE IF EXISTS schalen          CASCADE;
DROP TABLE IF EXISTS cursussen        CASCADE;
DROP TABLE IF EXISTS uitvoeringen     CASCADE;
DROP TABLE IF EXISTS inschrijvingen   CASCADE;
DROP TABLE IF EXISTS historie         CASCADE;
DROP TABLE IF EXISTS adressen         CASCADE;
