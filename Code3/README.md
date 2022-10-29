### ASSIGNMENT DUNGEON ADVENTURE GAME

Viene chiesto di sviluppare un'applicazione client server in cui il server gestisce le partite giocate in un semplice gioco, “Dungeon adventures” basato su una semplice interfaccia testuale.

A ogni giocatore a inizio del gioco viene chiesto di scegliere un giocatore e successivamente viene assegnato un livello X di salute e una quantità Y di una pozione, X e Y generati casualmente.
Ogni giocatore combatte con un mostro diverso. Anche al mostro assegnato a un giocatore viene associato, all'inizio del gioco un livello Z di salute generato casualmente.
Il gioco si svolge in round, a ogni round un giocatore può:

1. Combattere col mostro. Il combattimento si conclude decrementando il livello di salute del mostro e del giocatore. Se LG è il livello di salute attuale del giocatore, tale livello viene decrementato di un valore casuale X, con 0<X<=LG;

2. Bere una parte della pozione. Il livello di salute del giocatore viene incrementato di un valore proporzionale alla quantità di pozione bevuta, che è un valore generato casualmente;

3. Uscire dal gioco. In questo caso la partita viene considerata persa per il giocatore.

Il combattimento si conclude quando il giocatore o il mostro o entrambi hanno un valore di salute pari a 0: se il giocatore ha vinto o pareggiato, può chiedere di giocare nuovamente, se invece ha perso deve uscire dal gioco.
La seguente l'applicazione client server soddisfa i seguenti criteri:

- Il server:
riceve richieste di gioco da parte dei clients e gestisce ogni connessione in un diverso thread;
ogni thread riceve comandi dal client li esegue. Nel caso del comando “combattere”, simula il comportamento del mostro assegnato al client;
dopo aver eseguito ogni comando ne comunica al client l'esito;
comunica al client l'eventuale terminazione del  gioco, insieme con l'esito.

- Il client: 
si connette con il server e chiede iterativamente all'utente il comando da eseguire e lo invia al server;
attende un messaggio che segnala l'esito del comando;
nel caso di gioco concluso vittoriosamente, chiede all'utente se intende continuare a giocare e lo comunica al server.
