# 🐴 Horse Game
Un semplice gioco sviluppato con Vaadin in Java, dove l'obiettivo è muovere un cavallo su una scacchiera e visitare ogni casella una sola volta. Il gioco si basa sul movimento del cavallo negli scacchi, con un'interfaccia interattiva che permette di selezionare la dimensione della scacchiera e di interagire con essa cliccando sulle celle.

## Funzionalità
Seleziona la dimensione della scacchiera: Puoi scegliere tra 3 dimensioni predefinite (4x4, 5x5, 6x6).

Muovi il cavallo come negli scacchi: Il cavallo può muoversi in modo "a L", come nel gioco degli scacchi, e deve visitare tutte le caselle senza ripassare su quelle già visitate.

Game Over: Se il cavallo ritorna su una casella già visitata, il gioco finisce e puoi scegliere di giocare di nuovo o tornare al menu principale.

Vittoria: Se riesci a visitare tutte le caselle, vinci il gioco!

## Tecnologie
Java: Linguaggio principale per lo sviluppo del gioco.

Vaadin: Framework per la creazione dell'interfaccia utente web.

CSS: Per la personalizzazione dell'aspetto visivo delle schermate di gioco.

## Installazione
Clona il repository:

```
git clone https://github.com/tuo-username/horse-game.git
```
Importa il progetto in IntelliJ IDEA o nel tuo IDE preferito.

Compila e avvia il progetto:
Puoi avviare il progetto direttamente dal tuo IDE. Se stai usando Maven, puoi eseguire il comando:

```
mvn clean install
mvn spring-boot:run
```

Accedi al gioco:
Una volta avviato il server, puoi accedere al gioco nel tuo browser visitando:

```
http://localhost:8080
```

## Come giocare
Seleziona la dimensione della scacchiera: Usa il menù a discesa per scegliere la dimensione desiderata della scacchiera (4x4, 5x5, 6x6).

Inizia il gioco: Clicca su "🎮 Inizia a giocare" per iniziare la partita.

Muovi il cavallo: Clicca su una casella per posizionare il cavallo. Poi, clicca sulle celle dove vuoi muovere il cavallo. Ricorda, il cavallo si muove come nel gioco degli scacchi (a L).

Game Over: Se il cavallo ritorna su una casella già visitata, il gioco termina e potrai scegliere di riavviare o tornare al menu principale.

Vittoria: Se riesci a visitare tutte le caselle, il gioco ti dirà che hai vinto!

## Personalizzazioni
Dimensione della scacchiera: Puoi aggiungere nuove dimensioni modificando il codice nella classe StartView e passando la dimensione alla vista GameView.

Estensione del gioco: Puoi aggiungere nuove funzionalità come punteggi, livelli di difficoltà, o una modalità a tempo.

## Contributi
Se desideri contribuire a questo progetto, sentiti libero di fare una pull request. Ogni contributo è benvenuto!

## Licenza
Questo progetto è distribuito sotto la licenza MIT. Vedi il file LICENSE per maggiori dettagli.

