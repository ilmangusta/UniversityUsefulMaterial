import java.io.*;
import java.net.*;
import java.util.*;
    
/*
Sviluppare un'applicazione client server in cui il server gestisce le partite giocate in un semplice gioco, “Dungeon adventures” basato su una semplice interfaccia testuale

A ogni giocatore viene assegnato, a inizio del gioco, un livello X di salute e una quantità Y di una pozione, X e Y generati casualmente.
Ogni giocatore combatte con un mostro diverso. Anche al mostro assegnato a un giocatore viene associato, 
all'inizio del gioco un livello Z di salute generato casualmente.

Il gioco si svolge in round, a ogni round un giocatore può 
-combattere col mostro: il combattimento si conclude decrementando 
    il livello di salute del mostro e del giocatore. Se LG è il livello di salute attuale del giocatore, 
    tale livello viene decrementato di un valore casuale X, con 0<X<=LG

-bere una parte della pozione, il livello di salute del giocatore viene incrementato di un valore proporzionale alla quantità di pozione bevuta, 
    che è un valore generato casualmente 

-uscire dal gioco. In questo caso la partita viene considerata persa per il giocatore 
-il combattimento si conclude quando il giocatore o il mostro o entrambi hanno un valore di salute pari a 0: se il giocatore ha vinto o pareggiato, 
    può chiedere di giocare nuovamente, se invece ha perso deve uscire dal gioco.

Sviluppare un'applicazione client server che implementi Dungeon adventures.
il client 
si connette con il server e chiede iterativamente all'utente il comando da eseguire e lo invia al server. I comandi sono i seguenti 
1:combatti, 2: bevi pozione, 3: esci del gioco 
attende un messaggio che segnala l'esito del comando 
nel caso di gioco concluso vittoriosamente, chiede all'utente se intende continuare a giocare e lo comunica al server
*/

public class Client{
    public static void main(String[] args){
        Scanner scanner=null;
        Scanner in=null;
        String line;
        boolean end=false;
        while(true){
            try(Socket socket=new Socket("127.0.0.1",10005)){
                scanner=new Scanner(System.in);
                in=new Scanner(socket.getInputStream());
                PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

                System.out.println("Partita iniziata!\nScegli Giocatore:\n1.Mago Merlino;\n2.Legolas;\n3.Gnomo armato;\n4.Optimus Prime.\n");
                while(true){
                    line=scanner.nextLine();
                    out.println(line);
                    String mostro=in.nextLine();
                    if(mostro.contentEquals("no")){
                        System.out.println("Inserisci un numero!");
                        continue;
                    }
                    String[] mostri=mostro.split(" ");
                    System.out.println("Giocatore creato!\nNatura: "+mostri[0]+" - Vita: "+mostri[1]+"\n\n\n....Attenzione arriva un mostro!");
                    break;
                }    
                while(true){
                    System.out.println("Fai la tua scelta:\n1.Combatti il mostro;\n2.Bevi la pozione magica;\n3.Scappa!\n");
                    line=scanner.nextLine();
                    if(line.contentEquals("3")){
                        out.println("exit");
                        System.out.println(in.nextLine());
                        break;
                    }
                    out.println(line);
                    String output=in.nextLine();
                    if(output.contentEquals("won")){
                        System.out.println("Ehi giocatore, hai vinto! Giocare ancora?\nY/N\n");
                        out.println("exit");
                        line=scanner.nextLine();
                        if(line.contentEquals("N")){
                            end=true;
                            break;
                        }
                        break;
                    }
                    if(output.contentEquals("lost")){
                        System.out.println("Danni inflitti al giocatore: "+in.nextLine()+"\nGAME OVER!");
                        out.println("exit");
                        end=true;
                        break;
                    }
                    if(output.contentEquals("draw")){
                        System.out.println("Morti entrambi, pareggio! Giocare ancora?\nY/N\n");
                        out.println("exit");
                        line=scanner.nextLine();
                        if(line.contentEquals("N")){
                            end=true;
                            break;
                        }
                        break;
                    }
                    System.out.println(output);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage()+ " errore dal client!");
            }
            if(end){
                break;
            }
        }
        System.out.println("Partita conclusa!");
    }
}


   