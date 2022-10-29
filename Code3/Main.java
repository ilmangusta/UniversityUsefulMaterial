import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class Main {


    
    public static void main(String[] args){
        int i=1;
        //ServerSocket server=null;
        ExecutorService pool=Executors.newCachedThreadPool();

        while(true){
            try(ServerSocket server=new ServerSocket(10005)){
                //System.out.println("Provo ad accettare connesione!");
                pool.execute(new Server(server.accept(),i));
                System.out.printf("Connessione accettata...inizio gioco [id:%d]!\n",i);
                i++;
                server.close();
            }catch (Exception e) {
                System.out.println(e.getMessage()+ " errore dal main!");
            }

        }
    }
}
