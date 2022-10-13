
import java.util.*;
import java.util.concurrent.*;


public class Main {
    
    public static void main(String[] args){

        int[] laborat = new int[20]; //laboratorio 20 posti pc
        Lab lab=new Lab(laborat);
        int n_stud=Integer.parseInt(args[0]); //studenti
        int n_tesi=Integer.parseInt(args[1]); //tesisti
        int n_prof=Integer.parseInt(args[2]);//professori

        //System.out.printf("Numero stud: %d, tesisti: %d, prof: %d\n", n_stud, n_tesi, n_prof);
        BlockingQueue<Utente> sala=new ArrayBlockingQueue<Utente>(100); //coda degli utenti

        lab.init();
        for (int i=0; i<n_prof; i++){sala.add(new Utente("p",i,ThreadLocalRandom.current().nextInt(1,10),lab,sala));}
        for (int i=0; i<n_tesi; i++){sala.add(new Utente("t",i,ThreadLocalRandom.current().nextInt(1,10),lab,sala));}
        for (int i=0; i<n_stud; i++){sala.add(new Utente("s",i,ThreadLocalRandom.current().nextInt(1,10),lab,sala));}
        //inizializzo n studenti professori e tesisti con i relativi parametri 

        while(sala.size()!=0){ //scorro finche non e vuota la lista
            Thread utente=new Thread(sala.poll());
            utente.start(); 
            //System.out.printf("Numero utenti nella sala: %d\n",sala.size());
            try {
                Thread.sleep(400);
            } catch (Exception e) {
            }
        }
    }   
}
