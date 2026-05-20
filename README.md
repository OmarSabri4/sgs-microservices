# SGS - Sistema Gestione Scolastica

Progetto a microservizi Spring Boot + PostgreSQL con API Gateway, orchestrato con Docker Compose.

## Versioni

- **v1.0.0** — CRUD base Docenti e Studenti
- **v1.1.0** — Aggiunta documentazione interattiva OpenAPI/Swagger
- **v1.2.0** — API Gateway con Spring Cloud Gateway + Circuit Breaker (Resilience4j)

---

## Stack tecnologico

- **Java 21** + **Spring Boot 3.5.14**
- **Spring Cloud Gateway 2025.0.0** (API Gateway)
- **Resilience4j** (Circuit Breaker)
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL 16**
- **SpringDoc OpenAPI 2.8.8** (Swagger UI)
- **Docker** + **Docker Compose**
- **Lombok**

---

## Architettura

```
                        ┌─────────────────────────────────┐
                        │         API Gateway              │
         Client ───────▶│      localhost:8080              │
                        │  (Spring Cloud Gateway)          │
                        └────────────┬────────────────┬────┘
                                     │                │
                          /api/docenti/**    /api/studenti/**
                                     │                │
                        ┌────────────▼──┐  ┌──────────▼────┐
                        │   docenti-    │  │   studenti-   │
                        │   service     │  │   service     │
                        │  porta 8081   │  │  porta 8082   │
                        └──────┬────────┘  └──────┬────────┘
                               │                  │
                        ┌──────▼──────────────────▼────┐
                        │        PostgreSQL 16          │
                        │         porta 5433            │
                        │       schema: sgs_core        │
                        └──────────────────────────────┘
```

---

## Struttura del progetto

```
sgs-microservices/
├── docker-compose.yml
├── db/
│   └── init.sql                  ← schema + dati seed
├── gateway-service/              ← API Gateway (porta 8080)
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── docenti-service/              ← Microservizio Docenti (porta 8081)
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── studenti-service/             ← Microservizio Studenti (porta 8082)
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
git clone https://github.com/OmarSabri4/sgs-microservices.git
cd sgs-microservices
docker compose up --build
```

Al primo avvio il DB viene inizializzato automaticamente con schema e dati tramite `init.sql`.
I microservizi aspettano che il DB sia pronto prima di avviarsi (healthcheck).

> ⚠️ Per un reset completo (cancella anche i dati): `docker compose down -v`

---

## Servizi disponibili

| Servizio | Porta diretta | Tramite Gateway |
|---|---|---|
| API Gateway | `localhost:8080` | — |
| Docenti | `localhost:8081` | `localhost:8080/api/docenti` |
| Studenti | `localhost:8082` | `localhost:8080/api/studenti` |
| PostgreSQL | `localhost:5433` | — |

> In produzione si usa **solo la porta 8080** del gateway. Le porte 8081/8082 sono esposte per sviluppo locale.

---

## Documentazione API (Swagger UI)

| Servizio | URL diretto |
|---|---|
| Docenti | http://localhost:8081/swagger-ui.html |
| Studenti | http://localhost:8082/swagger-ui.html |

---

## Endpoint Docenti

| Metodo | URL | Descrizione |
|---|---|---|
| GET | `/api/docenti` | Lista tutti i docenti |
| GET | `/api/docenti/{id}` | Singolo docente per ID |
| GET | `/api/docenti?stato=attivo` | Filtra per stato |
| GET | `/api/docenti?cognome=rossi` | Filtra per cognome (parziale) |
| POST | `/api/docenti` | Crea nuovo docente |
| PUT | `/api/docenti/{id}` | Aggiorna docente |
| DELETE | `/api/docenti/{id}` | Elimina docente |

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

## Endpoint Studenti

| Metodo | URL | Descrizione |
|---|---|---|
| GET | `/api/studenti` | Lista tutti gli studenti |
| GET | `/api/studenti/{id}` | Singolo studente per ID |
| GET | `/api/studenti?idClasse=1` | Filtra per classe |
| GET | `/api/studenti?attivo=true` | Filtra per stato |
| GET | `/api/studenti?cognome=rossi` | Filtra per cognome (parziale) |
| POST | `/api/studenti` | Crea nuovo studente |
| PUT | `/api/studenti/{id}` | Aggiorna studente |
| DELETE | `/api/studenti/{id}` | Elimina studente |

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

## Circuit Breaker

Il gateway implementa un **Circuit Breaker** tramite Resilience4j. Se un microservizio non è raggiungibile, il gateway risponde con un messaggio di errore controllato invece di far attendere il client:

```json
{
  "status": "503",
  "messaggio": "Il servizio Docenti non è al momento disponibile. Riprovare più tardi."
}
```

---

## Connessione al DB con DBeaver

| Campo | Valore |
|---|---|
| Host | `localhost` |
| Porta | `5433` |
| Database | `sgs` |
| Utente | `sgs_user` |
| Password | `postgres` |

Le tabelle si trovano in: `sgs → Schemas → sgs_core → Tables`

Tabelle disponibili: `docenti`, `studenti`, `classi`, `materie`, `ruoli`, `utenti`, `utente_ruolo`, `docente_classe`, `docente_materia`, `audit`

---

## Comandi utili

```bash
# Avvia tutto (con rebuild delle immagini)
docker compose up --build

# Avvia in background
docker compose up -d

# Ferma i container (i dati rimangono)
docker compose down

# Reset completo (cancella anche il volume del DB)
docker compose down -v

# Log in tempo reale
docker compose logs -f

# Log di un singolo servizio
docker compose logs -f gateway-service
docker compose logs -f docenti-service
docker compose logs -f studenti-service
```

---

## Note di sicurezza

> ⚠️ Le credenziali presenti in questo progetto sono solo per uso locale/didattico.
> In produzione vanno gestite tramite variabili d'ambiente o un secret manager.

---

## Prossimi sviluppi

- [ ] Autenticazione JWT
- [ ] Jenkins Pipeline CI/CD
- [ ] Flyway per il versioning dello schema DB
