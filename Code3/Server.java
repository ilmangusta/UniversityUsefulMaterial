import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

//per la terminazione del server ho deciso di lasciare questa implementazione per realizzare la versione
//piu verosimilmente vicina alla realtà. Il socket server rimarrà sempre in attesa ad accettare nuove richieste 
//se compariranno client (giocatori) nuovi. In alternativa avrei potuto mettere un numero fissato
//di giocatori al massimo accettati dal server (per esempio scelti all'inizio del gioco o passati come parametro)

public class Server implements Runnable {

    public Socket socket;
    public int id;
    public Server(Socket socket,int id){
        this.socket=socket;
        this.id=id;
    }

    public int combatti(Player player,Monster monster){ //combatto, genero danno casuale e aggiorno vita
        int res=ThreadLocalRandom.current().nextInt(1,player.pv+1); //thread safe
        player.pv=player.pv-res;
        monster.pv=monster.pv-res;
        return res;
    }
    public int bevi(Player player){ //bevo pozione agiorno pozione e vita player
        if(player.potion<=0){return 0;}
        int quantita=ThreadLocalRandom.current().nextInt(1,player.potion+1); //rhead safe
        player.pv=player.pv+quantita;          
        player.potion=player.potion-quantita;      
        return quantita;
    }

    public void run(){
        //inizializzo variabili per lettura e scrittura su socket
        Scanner scanner=new Scanner(System.in);
        String line;
        Player player;
        Monster monster=new Monster();
        try {
            Scanner in=new Scanner(socket.getInputStream());
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            while(true){
                line=in.nextLine();
                if(line.contentEquals("1")){player=new Player("Mago");out.println("Mago "+player.pv);break;}
                if(line.contentEquals("2")){player=new Player("Elfo");out.println("Elfo "+player.pv);break;}
                if(line.contentEquals("3")){player=new Player("Gnomo");out.println("Gnomo "+player.pv);break;}
                if(line.contentEquals("4")){player=new Player("Robottone");out.println("Robottone "+player.pv);break;}
                else{out.println("no");} //non ho ricevuto input giusto
            }
            while(in.hasNextLine()){ //inizio comunicazione client-server
                line=in.nextLine();
                int res;
                String result;
                if(line.contentEquals("exit")){ //scappa
                    out.println("Hai fatto il fugone! Hai perso!");
                    break; //esci
                }
                else if(line.contentEquals("1")){
                    res=combatti(player,monster); //ritorna i danni subiti
                    if(monster.pv<=0 && player.pv<=0){
                        out.println("draw");
                        break;
                    }
                    if(monster.pv<=0){
                        out.println("won");
                        break;
                    }
                    if(player.pv<=0){
                        out.println("lost");
                        out.println(res);
                        break;
                    }
                    result="Danni inflitti "+res+ "! Vita totale mostro: "+(monster.pv)+" - Vita totale giocatore: "+(player.pv);
                    out.println(result);
                    continue;
                }
                else if(line.contentEquals("2")){ //bevi pozione
                    res=bevi(player); //quanta pozione beve
                    if(res==0){
                        out.println("Pozione finita!!");
                        continue;
                    }
                    String totalHp="Pozione bevuta! Vita totale: "+(player.pv)+ " - Pozione rimanente: "+player.potion;
                    out.println(totalHp);
                    continue;       
                }else{
                    out.println("Fail");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Partita terminata con successo!");
    }    
}
