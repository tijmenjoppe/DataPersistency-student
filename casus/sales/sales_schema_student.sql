-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'sales'
--
-- Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- ------------------------------------------------------------------------


-- DROP DATABASE sales;

CREATE DATABASE sales
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
