-- ============================================================
--  SGS - Sistema Gestione Scolastica
--  Init script eseguito automaticamente al primo avvio del DB
-- ============================================================

CREATE USER sgs_user WITH PASSWORD 'postgres';

CREATE DATABASE IF NOT EXISTS sgs;
\connect sgs;

CREATE SCHEMA IF NOT EXISTS sgs_core;
GRANT ALL ON SCHEMA sgs_core TO sgs_user;

-- ── Tabelle ──────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS sgs_core."Classi" (
    id_classe      SERIAL PRIMARY KEY,
    codice_classe  VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS sgs_core."Materie" (
    id_materia  SERIAL PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS sgs_core."Ruoli" (
    id_ruolo  SERIAL PRIMARY KEY,
    nome      VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS sgs_core."Docenti" (
    id_docente          SERIAL PRIMARY KEY,
    codice_docente      VARCHAR(20)  NOT NULL UNIQUE,
    nome                VARCHAR(50)  NOT NULL,
    cognome             VARCHAR(50)  NOT NULL,
    codice_fiscale      VARCHAR(16)  NOT NULL UNIQUE,
    email_istituzionale VARCHAR(100) NOT NULL UNIQUE,
    telefono            VARCHAR(20),
    data_assunzione     DATE,
    stato               VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS sgs_core."Studenti" (
    id_studente         SERIAL PRIMARY KEY,
    codice_studente     VARCHAR(20)  NOT NULL UNIQUE,
    nome                VARCHAR(50)  NOT NULL,
    cognome             VARCHAR(50)  NOT NULL,
    codice_fiscale      VARCHAR(16)  NOT NULL UNIQUE,
    data_nascita        DATE,
    indirizzo           VARCHAR(200),
    id_classe           INTEGER REFERENCES sgs_core."Classi"(id_classe),
    email_scuola        VARCHAR(100) UNIQUE,
    email_personale     VARCHAR(100),
    telefono            VARCHAR(20),
    telefono_emergenza  VARCHAR(20),
    attivo              BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS sgs_core."Utenti" (
    id_utente      SERIAL PRIMARY KEY,
    username       VARCHAR(50)  NOT NULL UNIQUE,
    password_hash  VARCHAR(255) NOT NULL,
    email          VARCHAR(100) NOT NULL UNIQUE,
    attivo         BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS sgs_core."Utente_Ruolo" (
    id_utente  INTEGER REFERENCES sgs_core."Utenti"(id_utente),
    id_ruolo   INTEGER REFERENCES sgs_core."Ruoli"(id_ruolo),
    PRIMARY KEY (id_utente, id_ruolo)
);

CREATE TABLE IF NOT EXISTS sgs_core."Docente_Classe" (
    id_docente  INTEGER REFERENCES sgs_core."Docenti"(id_docente),
    id_classe   INTEGER REFERENCES sgs_core."Classi"(id_classe),
    PRIMARY KEY (id_docente, id_classe)
);

CREATE TABLE IF NOT EXISTS sgs_core."Docente_Materia" (
    id_docente  INTEGER REFERENCES sgs_core."Docenti"(id_docente),
    id_materia  INTEGER REFERENCES sgs_core."Materie"(id_materia),
    PRIMARY KEY (id_docente, id_materia)
);

CREATE TABLE IF NOT EXISTS sgs_core."Audit" (
    id_audit          SERIAL PRIMARY KEY,
    tabella           VARCHAR(50),
    id_record         INTEGER,
    operazione        VARCHAR(10),
    autore            VARCHAR(50),
    valore_precedente TEXT,
    timestamp         TIMESTAMP DEFAULT NOW()
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA sgs_core TO sgs_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA sgs_core TO sgs_user;

-- ── Dati ─────────────────────────────────────────────────────

INSERT INTO sgs_core."Classi" (codice_classe) VALUES ('3A'), ('4B'), ('5C');

INSERT INTO sgs_core."Materie" (nome) VALUES ('Informatica'), ('Matematica'), ('Italiano'), ('Storia');

INSERT INTO sgs_core."Ruoli" (nome) VALUES ('amministratore'), ('segreteria'), ('coordinatore'), ('docente'), ('studente');

INSERT INTO sgs_core."Docenti" (codice_docente, nome, cognome, codice_fiscale, email_istituzionale, telefono, data_assunzione, stato) VALUES
('DOC-042', 'Marco',    'Orsetto',   'RSSTMR75C12H501X', 'm.orsetto@scuola.it',   '+39 02 1234567', '2018-09-01', 'attivo'),
('DOC-017', 'Laura',    'Ferretti',  'FRRLRA82A41F205Z', 'l.ferretti@scuola.it',  '+39 02 2345678', '2015-09-01', 'attivo'),
('DOC-031', 'Giovanni', 'Marchetti', 'MRCGNN70D03L219P', 'g.marchetti@scuola.it', '+39 02 3456789', '2010-09-01', 'attivo'),
('DOC-055', 'Sofia',    'Bianchi',   'BNCSFA90B41H501K', 's.bianchi@scuola.it',   '+39 02 4567890', '2020-09-01', 'attivo'),
('DOC-008', 'Luca',     'Conti',     'CNTLCU85E12G702Q', 'l.conti@scuola.it',     '+39 02 5678901', '2012-09-01', 'attivo'),
('DOC-063', 'Alessia',  'Ricci',     'RCCLS88H41F839M',  'a.ricci@scuola.it',     '+39 02 6789012', '2022-01-15', 'attivo'),
('DOC-024', 'Roberto',  'Esposito',  'SPTRRT68A03H501W', 'r.esposito@scuola.it',  '+39 02 7890123', '2008-09-01', 'attivo'),
('DOC-039', 'Chiara',   'Marino',    'MRNCHR92B41L483V', 'c.marino@scuola.it',    '+39 02 8901234', '2019-09-01', 'attivo'),
('DOC-011', 'Davide',   'Fontana',   'FNTDVD77C14F205R', 'd.fontana@scuola.it',   '+39 02 9012345', '2014-09-01', 'attivo'),
('DOC-047', 'Elena',    'Gallo',     'GLLLNE80D58H501Y', 'e.gallo@scuola.it',     '+39 02 0123456', '2016-09-01', 'attivo');

INSERT INTO sgs_core."Studenti" (codice_studente, nome, cognome, codice_fiscale, data_nascita, indirizzo, id_classe, email_scuola, email_personale, telefono, telefono_emergenza, attivo) VALUES
('STU-001', 'Alice',     'Moretti',   'MRTAL08A41H501B', '2008-01-15', 'Via Roma 12, Milano',             1, 'a.moretti@studenti.scuola.it',  'alice.moretti@gmail.com',   '+39 333 1111111', '+39 02 1111111', TRUE),
('STU-002', 'Filippo',   'Greco',     'GRCFLP07E14F205C','2007-05-14', 'Via Verdi 3, Milano',             1, 'f.greco@studenti.scuola.it',    'filippo.greco@gmail.com',   '+39 333 2222222', '+39 02 2222222', TRUE),
('STU-003', 'Valentina', 'Russo',     'RSSVLN08C41L219D','2008-03-21', 'Corso Buenos Aires 5, Milano',    1, 'v.russo@studenti.scuola.it',    'vale.russo@gmail.com',      '+39 333 3333333', '+39 02 3333333', TRUE),
('STU-004', 'Tommaso',   'Ferrari',   'FRRTMS07H12H501E','2007-08-12', 'Via Torino 8, Milano',            2, 't.ferrari@studenti.scuola.it',  'tommaso.f@gmail.com',       '+39 333 4444444', '+39 02 4444444', TRUE),
('STU-005', 'Ginevra',   'Costa',     'CSTGNV08B41F205F','2008-02-09', 'Via Dante 22, Milano',            2, 'g.costa@studenti.scuola.it',    'ginevra.costa@gmail.com',   '+39 333 5555555', '+39 02 5555555', TRUE),
('STU-006', 'Leonardo',  'Mancini',   'MNCLNR07C15G702G','2007-03-15', 'Via Napoleone 1, Milano',         2, 'l.mancini@studenti.scuola.it',  'leo.mancini@gmail.com',     '+39 333 6666666', '+39 02 6666666', TRUE),
('STU-007', 'Beatrice',  'Rizzo',     'RZZBRC08D41H501H','2008-04-30', 'Via Montenapoleone 7, Milano',    3, 'b.rizzo@studenti.scuola.it',    'bea.rizzo@gmail.com',       '+39 333 7777777', '+39 02 7777777', TRUE),
('STU-008', 'Mattia',    'Bruno',     'BRNMTT07F17L483I','2007-06-17', 'Via Garibaldi 14, Milano',        3, 'm.bruno@studenti.scuola.it',    'mattia.bruno@gmail.com',    '+39 333 8888888', '+39 02 8888888', TRUE),
('STU-009', 'Giorgia',   'De Santis', 'DSNGRG08A41A944L','2008-01-28', 'Via Brera 9, Milano',             3, 'g.desantis@studenti.scuola.it', 'giorgia.ds@gmail.com',      '+39 333 9999999', '+39 02 9999999', TRUE),
('STU-010', 'Andrea',    'Lombardo',  'LMBNDR07G20H501M','2007-07-20', 'Via Solferino 16, Milano',        3, 'a.lombardo@studenti.scuola.it', 'andrea.lombardo@gmail.com', '+39 333 0000001', '+39 02 0000001', TRUE);

INSERT INTO sgs_core."Docente_Classe" (id_docente, id_classe) VALUES
(1,1),(1,2),(2,1),(3,2),(4,3),(5,1),(5,3),(6,2),(7,3),(8,1),(9,2),(10,3);

INSERT INTO sgs_core."Docente_Materia" (id_docente, id_materia) VALUES
(1,1),(1,2),(2,3),(3,4),(4,1),(5,2),(6,3),(7,4),(8,1),(9,2),(10,3);
