import java.io.*;
import java.net.*;
import java.util.*;
 
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


   
