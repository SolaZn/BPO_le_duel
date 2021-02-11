package joueur;

import java.util.LinkedList;

public class MainJ {
    private LinkedList<Integer> main;

    public MainJ(Joueur J){
        this.main = new LinkedList<>();
        for(int i = 0; i < 6; ++i) {
            this.main.add(J.getPioche().getCartePioche());
        }
    }

    public String toString(Joueur J){
        StringBuilder a = new StringBuilder("cartes " + J.getNom() + " { ");
        for(int i = 0; i < this.main.size(); ++i){
            a.append(this.main.get(i));
            a.append(" ");
        }
        a.append("}");
        return a.toString();
    }

    public void jouerCarteMain(int idx){
        this.main.remove(idx);
    }

    public void chercherCarte(int carteAChercher){
        for(int i = 0; i < this.main.size(); ++i){
            if(carteAChercher == this.main.get(i)){
                jouerCarteMain(i);
            }
        }
    }

    public int showCarteMain(int idx){
        return this.main.get(idx);
    }

    public int getTailleMain(){
        return this.main.size();
    }

    public boolean isEmpty(){
        return this.main.size() == 0;
    }
}
