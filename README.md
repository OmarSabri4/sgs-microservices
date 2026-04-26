# SGS - Sistema Gestione Scolastica

Progetto composto da due microservizi Spring Boot + PostgreSQL, orchestrati con Docker Compose.

## Versioni
- **v1.0.0** — CRUD base Docenti e Studenti
- **v1.1.0** — Aggiunta documentazione interattiva OpenAPI/Swagger

---

## Stack tecnologico
- **Java 21** + **Spring Boot 3.5.14**
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL 16**
- **SpringDoc OpenAPI 2.8.8** (Swagger UI)
- **Docker** + **Docker Compose**
- **Lombok**

---

## Struttura del progetto

```
sgs/
├── docker-compose.yml
├── db/
│   └── init.sql              ← schema + dati, eseguito automaticamente al primo avvio
├── docenti-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── studenti-service/
    ├── Dockerfile
    ├── pom.xml
    └── src/
```

---

## Avvio rapido

### Prerequisiti
- [Docker Desktop](https://www.docker.com/products/docker-desktop)
- [Git](https://git-scm.com)

### Avvio
```bash
git clone https://github.com/tuousername/sgs-microservices.git
cd sgs-microservices
docker-compose up
```

Al primo avvio il DB viene inizializzato automaticamente con schema e dati tramite `init.sql`.
I microservizi aspettano che il DB sia pronto prima di avviarsi (healthcheck).

---

## Servizi disponibili

| Servizio | Porta | URL base |
|---|---|---|
| Docenti | 8081 | http://localhost:8081/api/docenti |
| Studenti | 8082 | http://localhost:8082/api/studenti |
| PostgreSQL | 5433 | localhost:5433 |

---

## Documentazione API (Swagger UI)

Ogni microservizio espone una documentazione interattiva degli endpoint tramite **OpenAPI 3**.

| Servizio | Swagger UI | API Docs (JSON) |
|---|---|---|
| Docenti | http://localhost:8081/swagger-ui.html | http://localhost:8081/api-docs |
| Studenti | http://localhost:8082/swagger-ui.html | http://localhost:8082/api-docs |

### Come usare Swagger UI
1. Avvia i servizi con `docker-compose up`
2. Apri il browser e vai su `http://localhost:8081/swagger-ui.html`
3. Espandi un endpoint cliccandoci sopra
4. Clicca **"Try it out"**
5. Inserisci i parametri richiesti
6. Clicca **"Execute"** per vedere la risposta in tempo reale

---

## Endpoint Docenti (porta 8081)

| Metodo | URL | Descrizione |
|---|---|---|
| GET | /api/docenti | Lista tutti i docenti |
| GET | /api/docenti/{id} | Singolo docente per ID |
| GET | /api/docenti?stato=attivo | Filtra per stato |
| GET | /api/docenti?cognome=rossi | Filtra per cognome |
| POST | /api/docenti | Crea nuovo docente |
| PUT | /api/docenti/{id} | Aggiorna docente |
| DELETE | /api/docenti/{id} | Elimina docente |

### Esempio POST /api/docenti
```json
{
  "codiceDocente": "DOC-099",
  "nome": "Mario",
  "cognome": "Rossi",
  "codiceFiscale": "RSSMRA80A01H501Z",
  "emailIstituzionale": "m.rossi@scuola.it",
  "telefono": "+39 02 9999999",
  "dataAssunzione": "2024-09-01",
  "stato": "attivo"
}
```

---

## Endpoint Studenti (porta 8082)

| Metodo | URL | Descrizione |
|---|---|---|
| GET | /api/studenti | Lista tutti gli studenti |
| GET | /api/studenti/{id} | Singolo studente per ID |
| GET | /api/studenti?idClasse=1 | Filtra per classe |
| GET | /api/studenti?attivo=true | Filtra per stato |
| GET | /api/studenti?cognome=rossi | Filtra per cognome |
| POST | /api/studenti | Crea nuovo studente |
| PUT | /api/studenti/{id} | Aggiorna studente |
| DELETE | /api/studenti/{id} | Elimina studente |

### Esempio POST /api/studenti
```json
{
  "codiceStudente": "STU-011",
  "nome": "Giulia",
  "cognome": "Neri",
  "codiceFiscale": "NRIGLI09A41H501T",
  "dataNascita": "2009-01-10",
  "indirizzo": "Via Manzoni 5, Milano",
  "idClasse": 1,
  "emailScuola": "g.neri@studenti.scuola.it",
  "emailPersonale": "giulia.neri@gmail.com",
  "telefono": "+39 333 1234567",
  "telefonoEmergenza": "+39 02 1234567",
  "attivo": true
}
```

---

## Connessione al DB con DBeaver

Per ispezionare il database direttamente con DBeaver:

1. Apri DBeaver e clicca su **"Nuova connessione"** (icona + in alto a sinistra)
2. Seleziona **PostgreSQL** e clicca **Avanti**
3. Inserisci i parametri:

| Campo | Valore |
|---|---|
| Host | localhost |
| Porta | 5433 |
| Database | sgs |
| Nome utente | sgs_user |
| Password | postgres |

4. Clicca **"Test connessione"** per verificare
5. Clicca **Fine**

Una volta connesso, lo schema con tutte le tabelle si trova in:
```
sgs → Schemas → sgs_core → Tables
```

Le tabelle disponibili sono: `docenti`, `studenti`, `classi`, `materie`, `ruoli`, `utenti`, `utente_ruolo`, `docente_classe`, `docente_materia`, `audit`.

---

## Comandi utili

```bash
# Avvia tutto
docker-compose up

# Avvia in background
docker-compose up -d

# Ferma i container (i dati rimangono)
docker-compose down

# Ferma e cancella anche il volume del DB (reset completo)
docker-compose down -v

# Vedi i log in tempo reale
docker-compose logs -f

# Vedi i log di un solo servizio
docker-compose logs -f docenti-service
```

---

## Credenziali DB

> ⚠️ Le credenziali presenti in questo progetto sono solo per uso locale/didattico.
> In un ambiente di produzione vanno gestite tramite variabili d'ambiente o secret manager.

---

## Prossimi sviluppi
- [ ] Autenticazione JWT
- [ ] API Gateway con Spring Cloud Gateway
- [ ] Jenkins Pipeline CI/CD
