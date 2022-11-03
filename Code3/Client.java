import java.io.*;
import java.net.*;
import java.util.*;
    
//In questa versione simpatica ho lasciato la scelta al giocatore del personaggio tra 4 possibilità. Ad ogni 
//round viene chiesto di eseguire la mossa e il client manderà il messaggio al server il quale gestirà 
//l'operazione e manda al client il risultato

public class Client{
    public static void main(String[] args){
        //inizializzo variabili per lettura da stdin
        Scanner scanner=null;
        Scanner in=null;
        String line;
        boolean end=false;
        while(true){
            try(Socket socket=new Socket("127.0.0.1",10005)){ //connetto al localhost e porta
                scanner=new Scanner(System.in);
                in=new Scanner(socket.getInputStream());
                PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
                //creato legame per stdin e fd per mandare a server (out)
                System.out.println("Partita iniziata!\nScegli Giocatore:\n1.Mago Merlino;\n2.Legolas;\n3.Gnomo armato;\n4.Optimus Prime.\n");
                while(true){ //provo a leggere il messaggio da tastiera stdin e mando al socket server 
                    line=scanner.nextLine();
                    out.println(line);
                    String mostro=in.nextLine();
                    if(mostro.contentEquals("no")){ //controllo se ho inserito un numero dal stdin
                        System.out.println("Inserisci un numero!");
                        continue;
                    }
                    String[] mostri=mostro.split(" "); //inizio game 
                    System.out.println("Giocatore creato!\nNatura: "+mostri[0]+" - Vita: "+mostri[1]+"\n\n\n....Attenzione arriva un mostro!");
                    break;
                }    
                int round=1;
                while(true){ //inizio comunicazione con server per mandare comando e ricevere esito
                    System.out.println("Round "+round+".\nFai la tua mossa!\n1.Combatti il mostro;\n2.Bevi la pozione magica;\n3.Scappa!\n");
                    round++;
                    line=scanner.nextLine();
                    if(line.contentEquals("3")){ 
                        out.println("exit"); //mando segnale fuga al server
                        System.out.println(in.nextLine());
                        end=true;
                        break;
                    }
                    out.println(line);
                    String output=in.nextLine();
                    if(output.contentEquals("Fail")){ //gestisco se inserisco errato
                        System.out.println("Hai inserito un carattere sbagliato! Riprova");
                        continue;
                    }
                    if(output.contentEquals("won")){ //controllo se ho vinto e chiedo se giocare ancora
                        System.out.println("Ehi giocatore, hai vinto! Giocare ancora?\nY/N\n");
                        out.println("exit");
                        line=scanner.nextLine();
                        if(line.contentEquals("N")){
                            end=true; //aggiorno segnale di terminazione per il client
                            break;
                        }
                        break;
                    }
                    if(output.contentEquals("lost")){ //printo messaggio ed esco
                        System.out.println("Danni inflitti al giocatore: "+in.nextLine()+"\nGAME OVER!");
                        out.println("exit");
                        end=true;
                        break;
                    }
                    if(output.contentEquals("draw")){ 
                        System.out.println("Morti entrambi, pareggio! Giocare ancora?\nY/N\n");
                        out.println("exit");
                        line=scanner.nextLine();//chiedo se giocare ancora 
                        if(line.contentEquals("N")){ 
                            end=true;
                            break;
                        }
                        break;
                    }
                    System.out.println(output);
                }
                //gestisco gli errori da catturare
            }catch (IOException e) {
                System.out.println(e.getMessage()+ "IO Error from client!");
            }catch (Exception e) {
                System.out.println(e.getMessage()+ "Error from client!");
            }
            if(end) break; //se giocatore non vuole piu giocare
        }
        System.out.println("Partita conclusa!");
    }
}


   

   
