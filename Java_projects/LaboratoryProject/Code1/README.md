Il laboratorio di Informatica del Polo Marzotto è utilizzato da tre tipi di utenti (studenti, tesisti e professori) ed ogni utente deve fare una richiesta al tutor per accedere al laboratorio. I computer del laboratorio sono numerati da 1 a 20. Le richieste di accesso sono diverse a seconda del tipo dell'utente: I professori accedono in modo esclusivo a tutto il laboratorio, poichè hanno necessità di utilizzare tutti i computer per effettuare prove in rete. I tesisti richiedono l'uso esclusivo di un solo computer, identificato dall'indice i.

1. Gli studenti richiedono l'uso esclusivo di un qualsiasi computer.
2. I professori hanno priorità su tutti nell'accesso al laboratorio,
3. i tesisti hanno priorità sugli studenti. Nessuno però può essere interrotto mentre sta usando un computer.

Programma Java che simula il comportamento degli utenti e del tutor. Il programma riceve in ingresso il numero di studenti, tesisti e professori che utilizzano il laboratorio ed attiva un thread per ogni utente. Ogni utente accede k volte al laboratorio, con k generato casualmente.

Compilazione con Makefile:

1. "make" per compilazione
2. "make run" per eseguire codice con parametri
3. "make class" per rimuovere file oggetto .class
