package Es.Assignment4;
import java.io.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;



/*
Scrivere un programma che conta le occorrenze dei caratteri alfabetici 
(lettere dalla "A" alla "Z") in un insieme di file di testo. 
Il programma prende in input una serie di percorsi di file testuali 
e per ciascuno di essi conta le occorrenze dei caratteri, 
ignorando eventuali caratteri non alfabetici (come per esempio le 
cifre da 0 a 9). Per ogni file, il conteggio viene effettuato da un 
apposito task e tutti i task attivati vengono gestiti tramite un pool 
di thread. I task registrano i loro risultati parziali all'interno di 
una ConcurrentHashMap. Prima di terminare, il programma stampa su un 
apposito file di output il numero di occorrenze di ogni carattere. 
Il file di output contiene una riga per ciascun carattere ed è formattato come segue:

<carattere 1>,<numero occorrenze>
<carattere 2>,<numero occorrenze>
...
<carattere N>,<numero occorrenze>


Esempio di file di output:

a,1281
b,315
c,261
d,302
...
*/


public class Main {

    public static void main(String[] args) {

        if(args.length<1){
            System.out.println("Inserisci file");
            System.exit(0);
        }
        int nthreads=args.length;
        ExecutorService pool=Executors.newFixedThreadPool(nthreads); 
        String file_dest="file_dest.txt";
        //devo farmi una hashmap per associare ogni carattere quante volte viene ripetuto 
        ConcurrentHashMap my_map=new ConcurrentHashMap<Character,Integer>();

        for(int i=0;i<args.length;i++){ //creo file per ogni argomento e lo passo al pool di thread
            File file=new File(args[i]); 
            try {
                pool.execute(new Task(file, file_dest, my_map)); //1 thread per ogni file
            } catch (Exception e) {
            } 
        }
        

        pool.shutdown(); //termino il thread pool
        try {
            if (!pool.awaitTermination(3000, TimeUnit.MILLISECONDS)) //se non ha ancora terminato dopo il delay fissato, eseguo una shotdownnow
                pool.shutdownNow();
        }
        catch (InterruptedException e) {pool.shutdownNow();}

        try{
            File myObj=new File(file_dest);
            try{
                myObj.createNewFile();
            }catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            Writer write=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_dest), "utf-8"));
            write.write("Tutte le occorrenze dei carattere");
            write.write("\n");

            //devo scrivere tutte le occorrenze in un file;
            Set<Map.Entry<Character, Integer>> entrySet = my_map.entrySet(); //creo iteratore per hashmap
            Iterator<Map.Entry<Character, Integer>> iterator = entrySet.iterator();
            while(iterator.hasNext()){ //itero per stampare i risultati
                Map.Entry<Character, Integer> entry=iterator.next(); 
                String str=String.valueOf(entry.getValue());
                write.write(entry.getKey());
                write.write(",");
                write.write(str);
                write.write("\n");
                //System.out.println(entry.getKey() + "," + str); 
            }
            write.close();
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
