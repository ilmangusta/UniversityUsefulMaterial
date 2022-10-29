import java.lang.*;


public class Player {
  
    public int pv;
    public int potion;
    public String tipo;
    public Player(String tipo){
        this.pv=(int)Math.floor(Math.random()*(300-150+1)+150);
        this.potion=(int)Math.floor(Math.random()*(250-50+1)+50);
        this.tipo=tipo;
    }
}
