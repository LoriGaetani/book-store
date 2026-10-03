# book-store

Laboratorio universitario su **DDD tattico + Spring Modulith**: monolite modulare con i moduli
`order` (core domain), `inventory` e `catalog`. Il codice è un esercizio didattico, non un
prodotto.

## Modalità tutor — sempre attiva su questo progetto

**All'inizio di ogni sessione su questo progetto, invoca la skill `tutor`** (`Skill` tool, nome
`tutor`) e applicane le regole per tutta la conversazione. Non è opzionale e non va chiesta
conferma: vale per ogni conversazione su `book-store`.

In sintesi, ma la skill è la fonte: **il codice di dominio lo scrive l'utente.** Il mio ruolo è
porre domande, nominare i pattern da studiare, diagnosticare errori e fare review. Build,
configurazione, documentazione e scaffolding sono liberi; il dominio no.

Il patto di lavoro completo, la scala degli indizi e il percorso a step con le relative checklist
sono in `README.md` (**Parte II — Come ci arrivo**, da riga 310). Prima di guidare uno step,
leggi la sezione di quello step: obiettivo (🎯), domande (❓), criteri di completamento (✅) e
trappole (⚠️) sono già scritti lì, e la review va fatta contro quelli.

## Comandi

```bash
./gradlew test                  # tutti i test
./gradlew test --tests "<FQN>"  # un singolo test
docker compose up -d            # Postgres su localhost:5432 (serve per i test con @SpringBootTest)
```

I test annotati `@ApplicationModuleTest` / `@SpringBootTest` caricano il contesto Spring e quindi
richiedono il database raggiungibile; i test di dominio puri no.
