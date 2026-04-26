# SGS - Sistema Gestione Scolastica

Progetto composto da due microservizi Spring Boot + PostgreSQL, orchestrati con Docker Compose.

## Struttura

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

## Avvio rapido (su qualsiasi PC con Docker)

```bash
git clone <url-repo>
cd sgs
docker-compose up --build
```

Al primo avvio il DB viene inizializzato automaticamente con schema e dati.

Servizi disponibili:
- Docenti:  http://localhost:8081/api/docenti
- Studenti: http://localhost:8082/api/studenti

## Build e push su Docker Hub

```bash
# 1. Build delle immagini
docker build -t <tuo-username>/sgs-docenti:latest ./docenti-service
docker build -t <tuo-username>/sgs-studenti:latest ./studenti-service

# 2. Login su Docker Hub
docker login

# 3. Push
docker push <tuo-username>/sgs-docenti:latest
docker push <tuo-username>/sgs-studenti:latest
```

Dopo il push, aggiorna il docker-compose.yml sostituendo `build:` con `image:`:

```yaml
docenti-service:
  image: <tuo-username>/sgs-docenti:latest

studenti-service:
  image: <tuo-username>/sgs-studenti:latest
```

Così su un altro PC basta `docker-compose up` senza dover ricompilare niente.

## Endpoint principali

### Docenti (porta 8081)
| Metodo | URL | Descrizione |
|--------|-----|-------------|
| GET | /api/docenti | Lista tutti i docenti |
| GET | /api/docenti/{id} | Singolo docente |
| GET | /api/docenti?stato=attivo | Filtra per stato |
| GET | /api/docenti?cognome=ros | Filtra per cognome |
| POST | /api/docenti | Crea nuovo docente |
| PUT | /api/docenti/{id} | Aggiorna docente |
| DELETE | /api/docenti/{id} | Elimina docente |

### Studenti (porta 8082)
| Metodo | URL | Descrizione |
|--------|-----|-------------|
| GET | /api/studenti | Lista tutti gli studenti |
| GET | /api/studenti/{id} | Singolo studente |
| GET | /api/studenti?idClasse=1 | Filtra per classe |
| GET | /api/studenti?attivo=true | Filtra per stato |
| GET | /api/studenti?cognome=ros | Filtra per cognome |
| POST | /api/studenti | Crea nuovo studente |
| PUT | /api/studenti/{id} | Aggiorna studente |
| DELETE | /api/studenti/{id} | Elimina studente |

## Fermare i container

```bash
docker-compose down          # ferma e rimuove i container (i dati nel volume rimangono)
docker-compose down -v       # ferma e cancella anche il volume del DB (reset completo)
```
