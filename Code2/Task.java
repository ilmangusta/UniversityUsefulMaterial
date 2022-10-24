package Es.Assignment4;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.*;


public class Task implements Runnable {

    public File file;
    public File file_destinaz;
    public String file_dest;
    public ConcurrentHashMap char_map;

    public Task(File name, String dest, ConcurrentHashMap map){
        this.file=name;
        this.file_dest=dest;
        this.char_map=map;
    }

    public void run(){
        try{
            FileReader fr=new FileReader(file);   //Creo file object 
            BufferedReader br=new BufferedReader(fr);
            int c=0;
            int old, newval;            
            while((c=br.read())!=-1){
                char chr=(char) c; //converto da int to char 
                Boolean flag=Character.isDigit(c);
                if(flag){
                   continue;
                }else {
                    synchronized(this.char_map){
                        chr=Character.toLowerCase(chr);
                        if(this.char_map.containsKey(chr)){
                            old=(int)this.char_map.get(chr);
                            newval=old+1;
                            this.char_map.put(chr,newval);
                        }else{
                            this.char_map.putIfAbsent(chr,1);
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
