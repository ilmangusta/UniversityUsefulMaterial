import java.io.*;
import java.net.*;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args){
        int i=1;
        //creo un pool di thread potenzialmente infinito che attende sempre nuovi client
        ExecutorService pool=Executors.newCachedThreadPool();
        while(true){
            try(ServerSocket server=new ServerSocket(10005)){
                pool.execute(new Server(server.accept(),i));
                //il main rimane in attesa finche non accetta una connessione da un client, a quel 
                //punto viene passsato al thread il socket per comunicare
                System.out.printf("Connessione accettata...inizio gioco [id:%d]!\n",i);
                i++;
                server.close(); //chiudo fd;
            }//gestisco gli errori da catturare
            catch (BindException e) {
                System.out.println(e.getMessage()+ "Bind error from main! Port busy!");
            }catch (IOException e) {
                System.out.println(e.getMessage()+ "IO Error!");
            }catch (Exception e) {
                System.out.println(e.getMessage()+ "Error from main!");
            }
        }
    }
}
