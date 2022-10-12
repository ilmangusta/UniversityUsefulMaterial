import java.util.*;
import java.util.concurrent.*;

public class Utente extends Thread {
    
    public String utente;
    public int id;
    public int k;
    public int pc_tesista;
    public Lab pc;
    public BlockingQueue<Utente> sala;

    public Utente(String tipo,int id, int random, Lab lab, BlockingQueue<Utente> sala){
        this.utente=tipo;
        this.id=id;
        this.k=random;
        this.pc=lab;
        this.sala=sala;
        this.pc_tesista=-1;
    }

    public void run(){ //classe run per thread 
        int pos;
        if(this.utente=="p"){
            this.pc.entraProf(this);
            System.out.printf("professore %d occupa sala\n", this.id);
            try {
                Thread.sleep(900);
            }
            catch (InterruptedException e) {
                System.err.println("Interruzione su sleep prof.");
            }
            //System.out.println("Operazione professore TERMINATA");
            this.pc.esceProf(); 
            this.k--;
            System.out.printf("prof %d deve entrare ancora %d volte\n",this.id, this.k);
            if(this.k>0){Thread prof=new Thread(this);prof.start();} //creo nuovo thread per stesso prof e starto thread
        }

        else if(this.utente=="t"){
            pos=this.pc.entraTesi(this);
            System.out.printf("tesista %d su computer %d\n",this.id,pos);
            try {
                Thread.sleep(1800);
            }
            catch (InterruptedException e) {
                System.err.println("Interruzione su sleep.");
            }
            //System.out.println("Operazione tesista TERMINATA");
            this.pc.esceTesista(this.pc,pos);
            this.k--;
            System.out.printf("tesisti %d deve entrare ancora %d volte\n",this.id, this.k);
            if(this.k>0){this.sala.add(this);} //mando nuovamente tesista nella coda
        }

        else{    
            pos=this.pc.entraStud(this);
            System.out.printf("studente %d su computer %d\n",this.id,pos);
            try {
                Thread.sleep(1200);
            }
            catch (InterruptedException e) {
                System.err.println("Interruzione su sleep.");
            }
            //System.out.println("Operazione studente TERMINATA: ");
            this.pc.esceStud(this.pc,pos);
            this.k--;
            System.out.printf("studente %d deve entrare ancora %d volte\n",this.id, this.k);
            if(this.k>0){this.sala.add(this);} //mando nuovamente il thread studente 
        }
    }
}
