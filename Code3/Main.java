import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class Main {    
    public static void main(String[] args){
        int i=1;
        ExecutorService pool=Executors.newCachedThreadPool();
        while(true){
            try(ServerSocket server=new ServerSocket(10005)){
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
