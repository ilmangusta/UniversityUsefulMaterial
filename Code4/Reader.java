//package Assign7;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
//importo libreria gson per utilizzare reader
import com.google.gson.stream.JsonReader;

public class Reader {
    public static void main(String[] args) {

        if (args.length != 1) { //controllo parametri
            System.out.println("Inserisci file");
            return;
        }
        //creo variabili atomiche per permettere l'incremento atomico e sincronizzato
        AtomicInteger Bonifico=new AtomicInteger(0);
        AtomicInteger Accredito=new AtomicInteger(0);
        AtomicInteger Bollettino=new AtomicInteger(0);
        AtomicInteger F24=new AtomicInteger(0);
        AtomicInteger PagoBancomat=new AtomicInteger(0);
        File input = new File(args[0]);
        ExecutorService pool=Executors.newFixedThreadPool(8); //pool con 8 thread

        try (JsonReader reader = new JsonReader(new FileReader(input))) {
            //apro array
            reader.beginArray();
            while (reader.hasNext()) {
                //apro object per lettura
                reader.beginObject();
                String owner = null;
                while (reader.hasNext()) {
                    String str = reader.nextName();
                    if (str.contentEquals("owner")) {
                        owner = reader.nextString();
                        continue;
                    }
                    if (str.contentEquals("records")) {
                        reader.beginArray();
                        ArrayList<Movimento> MovList=new ArrayList<Movimento>(); //creo lista di Movimenti
                        while (reader.hasNext()) {
                            //sto leggendo i movimenti;
                            reader.beginObject();
                            String mov;
                            String date=null;
                            while (reader.hasNext()) {
                                mov = reader.nextName();
                                if (mov.contentEquals("reason")) {
                                    String reason=reader.nextString();
                                    Movimento record=new Movimento(date, reason);
                                    MovList.add(record);
                                    continue;
                                }
                                date=reader.nextString();
                            }
                            reader.endObject();
                        }
                        reader.endArray();
                        ContoCorrente cc=new ContoCorrente(owner, MovList); //creo conto corrente con proprietario e lista Mov
                        Task t=new Task(cc,Bonifico,Bollettino,F24,PagoBancomat,Accredito); //nuovo thread e passo conto corrente e variabili atomiche
                        pool.execute(t); //avvio il thread
                    }
                }
                reader.endObject();
            }
            reader.endArray();
        } //controllo e catturo le eccezioni
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown(); //termino il thread pool entro un tot di secondi se non termina da solo
        try {
            if (!pool.awaitTermination(8000, TimeUnit.MILLISECONDS)) //se non ha ancora terminato dopo il delay fissato, eseguo una shotdownnow
                pool.shutdownNow();
        }catch (InterruptedException e) {pool.shutdownNow();}
        System.out.println("Programma Terminato!");
        System.out.println("Accredito: " +Accredito + "\n" +"F24: " +F24+ "\nBonifico: " +Bonifico+ "\nBollettino: " +Bollettino+ "\nPagoBancomat: " +PagoBancomat);
    }
}
