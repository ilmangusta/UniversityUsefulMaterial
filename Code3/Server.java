import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

//TODO: Move methods Combatti() and Bevi() to player class;

public class Server implements Runnable {

    public Socket socket;
    public int id;
    public Server(Socket socket,int id){
        this.socket=socket;
        this.id=id;
    }
    public int combatti(Player player,Monster monster){
        int res=(int)Math.floor(Math.random()*(player.pv-0+1)+0);
        player.pv=player.pv-res;
        monster.pv=monster.pv-res;
        return res;
    }
    public int bevi(Player player){
        if(player.potion<=0){
            return 0;
        }
        int quantita=(int)Math.floor(Math.random()*(player.potion-1+1)+1);
        player.pv=player.pv+quantita;          
        player.potion=player.potion-quantita;      
        return quantita;
    }

    public void run(){

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
                else{
                    out.println("no"); 
                }
            }
            while(in.hasNextLine()){
                line=in.nextLine();
                int res;
                String result;
                if(line.contentEquals("exit")){ //scappa
                    out.println("Hai fatto il fugone! Hai perso!");
                    break; //esci
                }
                if(line.contentEquals("1")){
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
                if(line.contentEquals("2")){ //bevi pozione
                    res=bevi(player); //quanta pozione beve
                    if(res==0){
                        out.println("Pozione finita!!");
                        continue;
                    }
                    String totalHp="Pozione bevuta! Vita totale: "+(player.pv)+ " - Pozione rimanente: "+player.potion;
                    out.println(totalHp);
                    continue;       
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Partita terminata con successo!");
    }    
}
