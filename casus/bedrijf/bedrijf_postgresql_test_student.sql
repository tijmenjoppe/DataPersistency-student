-- ------------------------------------------------------------------------
-- Data & Persistency - Casus 'bedrijf'
--
-- Testraamwerk SQL-opdrachten
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
--
--
-- Voer dit 'testsuite'-bestand uit op de aangemaakte 'bedijf' database.
-- Daarna kun je de opdrachten (b.v. S2_CRUD_student.sql) openen in
-- pgAdmin of een andere IDE. Na het maken van de opdrachten kun je je
-- uitwerkingen (grotendeels) testen. Zie het commentaar in de opdrachten
-- voor meer informatie.
-- ------------------------------------------------------------------------

DROP FUNCTION IF EXISTS test_select(_exercise_nr text);
CREATE OR REPLACE FUNCTION test_select(_exercise_nr text) RETURNS text AS $$
	DECLARE _sol_name text := REPLACE(LOWER(_exercise_nr), '.', '_');
	DECLARE _test_name text := _sol_name || '_test';
	DECLARE _query_res text;
	DECLARE _missing_count int := 0;
	DECLARE _excess_count int := 0;
	DECLARE _missing_query text := 'SELECT * FROM ' || _test_name || ' EXCEPT SELECT * FROM ' || _sol_name;
	DECLARE _excess_query text := 'SELECT * FROM ' || _sol_name || ' EXCEPT SELECT * FROM ' || _test_name;
	DECLARE _rows RECORD;
	BEGIN
		BEGIN
			EXECUTE 'SELECT * FROM ' || _sol_name INTO _query_res;
			IF POSITION('heeft nog geen uitwerking' IN _query_res) <> 0
				THEN RETURN _query_res;
			END IF;
		EXCEPTION
			WHEN OTHERS THEN
				RAISE notice 'mislukt: %', sqlerrm;
				NULL;
		END;

		FOR _rows IN EXECUTE _missing_query LOOP
			_missing_count := _missing_count + 1;
		END LOOP;

		FOR _rows IN EXECUTE _excess_query LOOP
			_excess_count := _excess_count + 1;
		END LOOP;

		IF _missing_count = 0 AND _excess_count = 0 THEN
			RETURN _exercise_nr || ' geeft de juiste resultaten!';
		ELSE
			RETURN _exercise_nr || ' geeft niet de juiste resultaten: er zijn ' || _excess_count::text || ' verkeerde rijen teveel en ' || _missing_count::text || ' goede rijen ontbreken.';
		END IF;
	EXCEPTION
		WHEN SQLSTATE '42804' THEN
			RETURN _exercise_nr || ' is niet correct: de kolomnamen of de kolomtypen kloppen niet.';
		WHEN SQLSTATE '42601' THEN
			RETURN _exercise_nr || ' is niet correct: het aantal kolommen klopt niet.';
	END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS test_exists(_exercise_nr TEXT, _row_test BIGINT, _compare_type TEXT);
CREATE OR REPLACE FUNCTION test_exists(_exercise_nr TEXT, _row_test BIGINT, _compare_type TEXT DEFAULT 'exact') RETURNS TEXT AS $$
	DECLARE _sol_name TEXT := REPLACE(LOWER(_exercise_nr), '.', '_');
	DECLARE _test_name TEXT := _sol_name || '_test';
	DECLARE _row_count INT := 0;
    BEGIN
		EXECUTE FORMAT('SELECT COUNT(*) FROM %I;', _test_name) INTO _row_count;

        IF _compare_type = 'exact' AND _row_count = _row_test THEN
            RETURN _exercise_nr || ' heeft de juiste uitwerking op de database!';
        ELSIF _compare_type = 'maximaal' AND _row_count <= _row_test THEN
            RETURN _exercise_nr || ' heeft de juiste uitwerking op de database!';
        ELSIF _compare_type = 'minimaal' AND _row_count >= _row_test THEN
            RETURN _exercise_nr || ' heeft de juiste uitwerking op de database!';
        END IF;

        RETURN _exercise_nr || ' heeft niet de juiste uitwerking op de database: er moeten ' || _compare_type || ' ' || _row_test || ' rijen of kolommen zijn, maar de database heeft er ' || _row_count || '.';
    END;
$$ LANGUAGE plpgsql;



DROP VIEW IF EXISTS s1_1_test; CREATE OR REPLACE VIEW s1_1_test AS
SELECT column_name FROM information_schema.columns WHERE table_schema='public' AND table_name='medewerkers' AND column_name='geslacht';

DROP VIEW IF EXISTS s1_2_test; CREATE OR REPLACE VIEW s1_2_test AS
SELECT m.naam FROM medewerkers m JOIN afdelingen a ON m.mnr = a.hoofd WHERE a.naam = 'ONDERZOEK' AND m.chef = 7839;

DROP VIEW IF EXISTS s1_4_test; CREATE OR REPLACE VIEW s1_4_test AS
SELECT column_name FROM information_schema.columns WHERE table_schema='public' AND table_name='adressen' AND column_name IN ('postcode', 'huisnummer', 'ingangsdatum', 'einddatum', 'telefoon', 'med_mnr');

-- Ongebruikt.
DROP VIEW IF EXISTS s1_5_test; CREATE OR REPLACE VIEW s1_5_test AS
SELECT naam FROM medewerkers WHERE mnr < 9000;



DROP VIEW IF EXISTS s2_1; CREATE OR REPLACE VIEW s2_1 AS SELECT 'S2.1 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s2_2; CREATE OR REPLACE VIEW s2_2 AS SELECT 'S2.2 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s2_3; CREATE OR REPLACE VIEW s2_3 AS SELECT 'S2.3 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s2_4; CREATE OR REPLACE VIEW s2_4 AS SELECT 'S2.4 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;


DROP VIEW IF EXISTS s2_1_test; CREATE OR REPLACE VIEW s2_1_test AS
SELECT code, omschrijving FROM cursussen WHERE code IN ('S02', 'JAV', 'GEN');

DROP VIEW IF EXISTS s2_2_test; -- CREATE OR REPLACE VIEW s2_2_test AS
-- SELECT * FROM medewerkers;

DROP VIEW IF EXISTS s2_3_test; CREATE OR REPLACE VIEW s2_3_test AS
SELECT * FROM (VALUES
    ('OAG'::VARCHAR(4), '2019-08-10'::DATE),
    ('S02',	'2019-10-04'),
    ('JAV',	'2019-12-13'),
    ('XML',	'2020-09-18'),
    ('RSO',	'2021-02-24')
) answer (cursus, begindatum);

DROP VIEW IF EXISTS s2_4_test; CREATE OR REPLACE VIEW s2_4_test AS
SELECT naam, voorl FROM medewerkers WHERE mnr != 7900;

DROP VIEW IF EXISTS s2_5_test; CREATE OR REPLACE VIEW s2_5_test AS
SELECT cursus
FROM uitvoeringen JOIN medewerkers ON mnr = docent
WHERE naam = 'SMIT' AND cursus = 'S02' AND
	EXTRACT(MONTH FROM begindatum) = 3 AND
	EXTRACT(DAY FROM begindatum) = 2;

DROP VIEW IF EXISTS s2_6_test; CREATE OR REPLACE VIEW s2_6_test AS
SELECT naam FROM medewerkers WHERE mnr >= 8000 AND functie = 'STAGIAIR';

DROP VIEW IF EXISTS s2_7_test; CREATE OR REPLACE VIEW s2_7_test AS
SELECT snr FROM schalen;



DROP VIEW IF EXISTS s3_1; CREATE OR REPLACE VIEW s3_1 AS SELECT 'S3.1 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s3_2; CREATE OR REPLACE VIEW s3_2 AS SELECT 'S3.2 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s3_3; CREATE OR REPLACE VIEW s3_3 AS SELECT 'S3.3 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s3_4; CREATE OR REPLACE VIEW s3_4 AS SELECT 'S3.4 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s3_5; CREATE OR REPLACE VIEW s3_5 AS SELECT 'S3.5 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s3_6; CREATE OR REPLACE VIEW s3_6 AS SELECT 'S3.6 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;


DROP VIEW IF EXISTS s3_1_test; CREATE OR REPLACE VIEW s3_1_test AS
SELECT * FROM (VALUES
	('S02'::VARCHAR(4), '2019-04-12'::DATE, 4::NUMERIC(2), 'SPIJKER'::VARCHAR(12)),
	('OAG', '2019-08-10', 1, 'JANSEN'),
    ('S02', '2019-10-04', 4, 'SMIT'),
    ('S02', '2019-12-13', 4, 'SMIT'),
	('JAV', '2019-12-13', 4, 'JANSEN'),
	('XML', '2020-02-03', 2, 'SMIT'),
	('JAV', '2020-02-01', 4, 'ADAMS'),
	('PLS', '2020-09-11', 1, 'SCHOTTEN'),
	('OAG', '2020-09-27', 1, 'SPIJKER'),
	('RSO', '2021-02-24', 2, 'SCHOTTEN')
) answer (code, begindatum, lengte, naam);

DROP VIEW IF EXISTS s3_2_test; CREATE OR REPLACE VIEW s3_2_test AS
SELECT * FROM (VALUES
    ('ALDERS'::VARCHAR(12), 'SPIJKER'::VARCHAR(12)),
    ('BLAAK', 'SMIT'),
    ('BLAAK', 'SPIJKER'),
    ('SCHOTTEN', 'SMIT'),
    ('DE KONING', 'SMIT'),
    ('ADAMS', 'SPIJKER'),
    ('SPIJKER', 'SMIT'),
    ('SPIJKER', 'SMIT'),
    ('MOLENAAR', 'SPIJKER')
) answer (cursist, docent);

DROP VIEW IF EXISTS s3_3_test; CREATE OR REPLACE VIEW s3_3_test AS
SELECT * FROM (VALUES
    ('HOOFDKANTOOR'::VARCHAR(20), 'CLERCKX'::VARCHAR(12)),
    ('OPLEIDINGEN', 'JANSEN'),
    ('VERKOOP', 'BLAAK'),
    ('PERSONEELSZAKEN', 'DE KONING')
) answer (afdeling, hoofd);

DROP VIEW IF EXISTS s3_4_test; CREATE OR REPLACE VIEW s3_4_test AS
SELECT * FROM (VALUES
    ('ALDERS'::VARCHAR(12), 'VERKOOP'::VARCHAR(20), 'UTRECHT'::VARCHAR(20)),
    ('DE WAARD',    'VERKOOP',      'UTRECHT'),
    ('JANSEN',      'OPLEIDINGEN',  'DE MEERN'),
    ('MARTENS',     'VERKOOP',      'UTRECHT'),
    ('BLAAK',       'VERKOOP',      'UTRECHT'),
    ('CLERCKX',     'HOOFDKANTOOR', 'LEIDEN'),
    ('SCHOTTEN',    'OPLEIDINGEN',  'DE MEERN'),
    ('DE KONING',   'HOOFDKANTOOR', 'LEIDEN'),
    ('DEN DRAAIER', 'VERKOOP',      'UTRECHT'),
    ('ADAMS',       'OPLEIDINGEN',  'DE MEERN'),
    ('SPIJKER',     'OPLEIDINGEN',  'DE MEERN'),
    ('MOLENAAR',    'HOOFDKANTOOR', 'LEIDEN'),
    ('SMIT',        'OPLEIDINGEN',  'DE MEERN'),
    ('JANSEN',      'VERKOOP',      'UTRECHT')
) answer (afdeling, hoofd);

DROP VIEW IF EXISTS s3_5_test; CREATE OR REPLACE VIEW s3_5_test AS
SELECT naam FROM medewerkers WHERE mnr IN (7499, 7934, 7698, 7876);

DROP VIEW IF EXISTS s3_6_test; CREATE OR REPLACE VIEW s3_6_test AS
SELECT * FROM (VALUES
    ('ALDERS'::VARCHAR(12), 100.00::NUMERIC(6, 2)),
    ('DE WAARD', 50.00),
    ('JANSEN', 200.00),
    ('MARTENS', 50.00),
    ('BLAAK', 200.00),
    ('CLERCKX', 200.00),
    ('SCHOTTEN', 200.00),
    ('DE KONING', 500.00),
    ('DEN DRAAIER', 100.00),
    ('ADAMS', 0.00),
    ('SPIJKER', 200.00),
    ('MOLENAAR', 50.00),
    ('SMIT', 0.00),
    ('JANSEN', 0.00)
  ) answer (naam, toelage);



DROP VIEW IF EXISTS s4_1; CREATE OR REPLACE VIEW s4_1 AS SELECT 'S4.1 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s4_2; CREATE OR REPLACE VIEW s4_2 AS SELECT 'S4.2 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s4_3; CREATE OR REPLACE VIEW s4_3 AS SELECT 'S4.3 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s4_4; CREATE OR REPLACE VIEW s4_4 AS SELECT 'S4.4 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s4_5; CREATE OR REPLACE VIEW s4_5 AS SELECT 'S4.5 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s4_6; CREATE OR REPLACE VIEW s4_6 AS SELECT 'S4.6 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s4_7; CREATE OR REPLACE VIEW s4_7 AS SELECT 'S4.7 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;


DROP VIEW IF EXISTS s4_1_test; CREATE OR REPLACE VIEW s4_1_test AS
SELECT * FROM (VALUES
    (7654::NUMERIC(4),	'VERKOPER'::VARCHAR(10),	'1976-09-28'::DATE),
    (7788, 'TRAINER', 	'1979-11-26'),
    (7902, 'TRAINER', 	'1979-02-13')
) answer (mnr, functie, gbdatum);


DROP VIEW IF EXISTS s4_2_test; CREATE OR REPLACE VIEW s4_2_test AS
SELECT * FROM (VALUES
    ('DE WAARD'::VARCHAR(12)),
    ('DE KONING'),
    ('DEN DRAAIER')
) answer (naam);

DROP VIEW IF EXISTS s4_3_test; CREATE OR REPLACE VIEW s4_3_test AS
SELECT * FROM (VALUES
    ('JAV'::VARCHAR(4),	'2019-12-13'::DATE,	5::BIGINT),
    ('OAG',	'2019-08-10',	3),
    ('S02',	'2019-04-12',	4),
    ('S02',	'2019-10-04',	3)
) answer (cursus, begindatum, aantal_inschrijvingen);

DROP VIEW IF EXISTS s4_4_test; CREATE OR REPLACE VIEW s4_4_test AS
SELECT * FROM (VALUES
    (7788::NUMERIC(4),	'JAV'::VARCHAR(4)),
    (7902,	'S02'),
    (7698,	'S02')
) answer (cursist, cursus);

DROP VIEW IF EXISTS s4_5_test; CREATE OR REPLACE VIEW s4_5_test AS
SELECT * FROM (VALUES
    ('JAV'::VARCHAR(4),	2::BIGINT),
    ('S02',	3),
    ('PRO',	1),
    ('OAG',	2),
    ('RSO',	1),
    ('ERM',	1),
    ('PLS',	1),
    ('XML',	2)
) answer (cursus, aantal);

-- Geen test voor S4.6 mogelijk.

DROP VIEW IF EXISTS s4_7_test; CREATE OR REPLACE VIEW s4_7_test AS
SELECT * FROM (VALUES
    (14::BIGINT, 150::NUMERIC, 525::NUMERIC)
) answer (aantal_medewerkers, commissie_medewerkers, commissie_verkopers);



DROP VIEW IF EXISTS s5_1; CREATE OR REPLACE VIEW s5_1 AS SELECT 'S5.1 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_2; CREATE OR REPLACE VIEW s5_2 AS SELECT 'S5.2 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_3; CREATE OR REPLACE VIEW s5_3 AS SELECT 'S5.3 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_4a; CREATE OR REPLACE VIEW s5_4a AS SELECT 'S5.4a heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_4b; CREATE OR REPLACE VIEW s5_4b AS SELECT 'S5.4b heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_5; CREATE OR REPLACE VIEW s5_5 AS SELECT 'S5.5 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_6; CREATE OR REPLACE VIEW s5_6 AS SELECT 'S5.6 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_7; CREATE OR REPLACE VIEW s5_7 AS SELECT 'S5.7 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s5_8; CREATE OR REPLACE VIEW s5_8 AS SELECT 'S5.8 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;


DROP VIEW IF EXISTS s5_1_test; CREATE OR REPLACE VIEW s5_1_test AS
SELECT * FROM (VALUES
    (7499::NUMERIC(4))
) answer (cursist);

DROP VIEW IF EXISTS s5_2_test; CREATE OR REPLACE VIEW s5_2_test AS
SELECT * FROM (VALUES
    (7934::NUMERIC(4)),
    (7839),
    (7782),
    (7499),
    (7900),
    (7844),
    (7698),
    (7521),
    (7654)
) answer (mnr);

DROP VIEW IF EXISTS s5_3_test; CREATE OR REPLACE VIEW s5_3_test AS
SELECT * FROM (VALUES
    (7902::NUMERIC(4)),
    (7934),
    (7369),
    (7654),
    (7521),
    (7844),
    (7900)
) answer (mnr);

DROP VIEW IF EXISTS s5_4a_test; CREATE OR REPLACE VIEW s5_4a_test AS
SELECT * FROM (VALUES
    ('JANSEN'::VARCHAR(12)),
    ('CLERCKX'),
    ('SCHOTTEN'),
    ('DE KONING'),
    ('SPIJKER'),
    ('BLAAK')
) answer (naam);

DROP VIEW IF EXISTS s5_4b_test; CREATE OR REPLACE VIEW s5_4b_test AS
SELECT * FROM (VALUES
    ('SMIT'::VARCHAR(12)),
    ('ALDERS'),
    ('DE WAARD'),
    ('MARTENS'),
    ('ADAMS'),
    ('JANSEN'),
    ('MOLENAAR'),
    ('DEN DRAAIER')
) answer (naam);

DROP VIEW IF EXISTS s5_5_test; CREATE OR REPLACE VIEW s5_5_test AS
SELECT * FROM (VALUES
    ('XML'::VARCHAR(12), '2020-02-03'::DATE),
    ('JAV',	'2020-02-01'),
    ('PLS',	'2020-09-11'),
    ('XML',	'2020-09-18')
) answer (cursus, begindatum);

DROP VIEW IF EXISTS s5_6_test; CREATE OR REPLACE VIEW s5_6_test AS
SELECT * FROM (VALUES
    ('S02'::VARCHAR(12),	'2019-04-12'::DATE,	4::BIGINT),
    ('OAG',	'2019-08-10',	3),
    ('S02',	'2019-10-04',	3),
    ('S02',	'2019-12-13',	2),
    ('JAV',	'2019-12-13',	5),
    ('JAV',	'2020-02-01',	3),
    ('XML',	'2020-02-03',	2),
    ('PLS',	'2020-09-11',	3),
    ('XML',	'2020-09-18',	0),
    ('OAG',	'2020-09-27',	1),
    ('ERM',	'2021-01-15',	0),
    ('PRO',	'2021-02-19',	0),
    ('RSO',	'2021-02-24',	0)
) answer (cursus, begindatum, aantal_inschrijvingen);

DROP VIEW IF EXISTS s5_7_test; CREATE OR REPLACE VIEW s5_7_test AS
SELECT * FROM (VALUES
    ('N'::VARCHAR(5), 'SMIT'::VARCHAR(12))
) answer (cursist);

DROP VIEW IF EXISTS s5_8_test; CREATE OR REPLACE VIEW s5_8_test AS
SELECT * FROM (VALUES
    ('DE KONING'::VARCHAR(12)),
    ('MOLENAAR'),
    ('MARTENS'),
    ('DE WAARD'),
    ('BLAAK'),
    ('JANSEN'),
    ('ALDERS'),
    ('CLERCKX'),
    ('DEN DRAAIER')
) answer (naam);



DROP VIEW IF EXISTS s9_1; CREATE OR REPLACE VIEW s9_1 AS SELECT 'S9.1 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s9_3; CREATE OR REPLACE VIEW s9_3 AS SELECT 'S9.3 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s9_4; CREATE OR REPLACE VIEW s9_4 AS SELECT 'S9.4 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;
DROP VIEW IF EXISTS s9_5; CREATE OR REPLACE VIEW s9_5 AS SELECT 'S9.5 heeft nog geen uitwerking - misschien moet je de DROP VIEW ... regel nog activeren?' AS resultaat;


DROP VIEW IF EXISTS s9_1_test; CREATE OR REPLACE VIEW s9_1_test AS
SELECT m.mnr
FROM medewerkers m, afdelingen a, historie h
WHERE m.afd = 40 AND h.mnr = a.hoofd AND m.chef = 7839
    AND m.mnr = h.mnr AND h.einddatum IS NULL AND m.maandsal = h.maandsal;

DROP VIEW IF EXISTS s9_3_test; CREATE OR REPLACE VIEW s9_3_test AS
SELECT * FROM (VALUES
    ('Overgang naar opleidingen, met salaris"correctie" 150'::VARCHAR(60),	7369::NUMERIC(4)),
    ('Targets al weer niet gehaald; salarisverlaging',	7499),
    ('Valt toch een beetje tegen.',	                    7654),
    ('Gepromoveerd tot hoofd van de afdeling verkoop',	7698),
    ('Aangenomen als manager voor het hoofdkantoor',	7782),
    ('Junior verkoper -- moet nog veel leren...',	    7900)
) answer (opmerkingen, mnr);

DROP VIEW IF EXISTS s9_4_test; CREATE OR REPLACE VIEW s9_4_test AS
SELECT * FROM (VALUES
    ('CLERCKX'::VARCHAR(12),	'2008-07-01'::DATE,	'HOOFDKANTOOR'::VARCHAR(20),	4::NUMERIC(2)),
    ('DE KONING',	'2002-01-01',	'VERKOOP',	    1),
    ('DE KONING',	'2002-08-01',	'VERKOOP',	    1),
    ('DE KONING',	'2004-05-15',	'VERKOOP',	    3),
    ('DE KONING',	'2005-01-01',	'VERKOOP',	    3),
    ('DE KONING',	'2005-07-01',	'HOOFDKANTOOR',	3),
    ('DE KONING',	'2005-11-01',	'HOOFDKANTOOR',	4),
    ('DE KONING',	'2006-02-01',	'HOOFDKANTOOR',	4),
    ('DE KONING',	'2009-06-15',	'HOOFDKANTOOR',	4),
    ('DE KONING',	'2013-12-01',	'HOOFDKANTOOR',	5),
    ('DE KONING',	'2015-09-01',	'HOOFDKANTOOR',	5),
    ('DE KONING',	'2017-10-01',	'HOOFDKANTOOR',	5),
    ('DE KONING',	'2018-10-01',	'HOOFDKANTOOR',	5),
    ('DE KONING',	'2019-11-01',	'HOOFDKANTOOR',	5),
    ('DE KONING',	'2020-02-15',	'HOOFDKANTOOR',	5),
    ('MOLENAAR',	'2018-02-01',	'HOOFDKANTOOR',	2),
    ('MOLENAAR',	'2018-05-01',	'HOOFDKANTOOR',	2),
    ('MOLENAAR',	'2019-02-01',	'HOOFDKANTOOR',	2),
    ('MOLENAAR',	'2020-01-01',	'HOOFDKANTOOR',	2)
) answer (naam, begindatum, afdeling, schaal);

DROP VIEW IF EXISTS s9_5_test; CREATE OR REPLACE VIEW s9_5_test AS
SELECT * FROM (VALUES
    ('SMIT',	    0::DOUBLE PRECISION),
    ('ALDERS',	    11),
    ('DE WAARD',	13),
    ('JANSEN',	    17),
    ('MARTENS',	    0),
    ('BLAAK',	    7),
    ('CLERCKX',	    0),
    ('SCHOTTEN',	17),
    ('DE KONING',	18),
    ('ADAMS',	    0),
    ('JANSEN',	    0),
    ('SPIJKER',	    1),
    ('MOLENAAR',	1),
    ('DEN DRAAIER',	5)
) answer (naam, tijdsduur);
