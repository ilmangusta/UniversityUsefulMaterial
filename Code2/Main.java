package Es.Assignment4;
import java.io.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

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
            }
            write.close();
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
