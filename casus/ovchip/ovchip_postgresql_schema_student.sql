-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'ov-chipkaart'
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
--
-- Creëer de database en het schema
-- ------------------------------------------------------------------------


-- DROP DATABASE ovchip;

CREATE DATABASE ovchip
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    -- LC_COLLATE = 'Dutch_Netherlands.1252'
    -- LC_CTYPE = 'Dutch_Netherlands.1252'
    -- TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


-- DROP SCHEMA public;

CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;
