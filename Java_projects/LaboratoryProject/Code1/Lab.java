import java.util.*;

public class Lab {
    public int[] pc2;
    public int sala_piena;
    public int are_stud;

    public Lab(int[] labor){
        this.pc2=labor;
        this.sala_piena=0; //per gestire quando piena
        this.are_stud=0; //per gestire quando c e almeno uno studente
    }

    public void init(){ //inizializzo la sala del laboratorio vuota
        for(int i=0; i<20; i++) this.pc2[i]=0;
    }

    public int pc_tesista_occupato(Lab lab, Utente utente){
        if (utente.pc_tesista!=-1){
            for(int i=0; i<20; i++){
                if(i==utente.pc_tesista && lab.pc2[i]==1){return 1;} //posto tesista occupato
                else return 0; //posto tesista libero
            }
        }
        return 0;
    }

    public synchronized void entraProf(Utente utente){
        while(this.sala_piena==1 || this.are_stud==1){ //non ho trovato spazio nel laboratorio o c e almeno uno stud
            //System.out.println("Non ce spazio per il prof: "+utente.id);
            try {
                wait();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        if(this.sala_piena==0 && this.are_stud==0){ //sala libera prof puo lavorare;
            this.sala_piena=1;
            return;
        }
    }
    
    public synchronized int entraTesi(Utente utente){
        int pos=0;
        while(this.sala_piena==1 || pc_tesista_occupato(this, utente)==1){ //non ho trovato spazio nel laboratorio
            //System.out.println("Non ce spazio per tesista: "+utente.id);
            try {
                wait();
            } catch (Exception e) {
            }
        }
        if(this.sala_piena==0){
            this.are_stud=1; //almeno uno studente nel laboratorio
            for(int i=0;i<20;i++){
                if (this.pc2[i]==0){
                    if(utente.pc_tesista==-1){utente.pc_tesista=i;} //controllo se il posto e gia assegnato
                    this.pc2[i]=1;
                    pos=i;
                    break;
                }
            }
        }
        return pos;
    }

    public synchronized int entraStud(Utente utente){
        int pos=0;
        while(this.sala_piena==1){ //non ho trovato spazio nel laboratorio 
            //System.out.println("Non ce spazio per studente: "+utente.id);
            try {
                wait();
            } catch (Exception e) {
            }
        }
        if (this.sala_piena==0){ //ho trovato spazio nel laborator
            this.are_stud=1; //almeno un studente nel laboratorio
            for(int i=0;i<20;i++){
                if (this.pc2[i]==0){
                    this.pc2[i]=1;
                    pos=i;
                    break;
                }
            }
        }
        return pos;
    }

    public synchronized void esceTesista(Lab lab,int i){ //metodo per gestire l'uscita del tesista
        lab.are_stud=0;
        lab.pc2[i]=0; //libero postazione
        //System.out.println("tesista uscito");
        notifyAll();
    }
    public synchronized void esceStud(Lab lab, int i){ //metodo per gestire l'uscita degli studenti
        lab.are_stud=0;
        lab.pc2[i]=0; //libero la postazione
        //System.out.println("Studente uscito");
        notifyAll();
    }
    public synchronized void esceProf(){ //metodo per gestire l'uscita dei professori
        this.sala_piena=0;
        this.are_stud=0;
        //System.out.println("Prof uscito");
        notifyAll(); 
    }
}
