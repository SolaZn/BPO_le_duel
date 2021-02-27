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
            if (this.main.get(i) < 10) {
                a.append("0");
            }
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

    public void setCarteMain(int carte){
        this.main.add(carte);
    }

    public int remplirMain(Joueur J){
        int nbCartesAPiocher;
        boolean aJoueAilleurs = J.getJeuHostile();
        if (aJoueAilleurs){
            nbCartesAPiocher = 6 - this.main.size();
        }
        else
            nbCartesAPiocher = 2;

        for(int i = 0; i < nbCartesAPiocher; ++i) {
            this.main.add(J.getPioche().getCartePioche());
        }
        return nbCartesAPiocher;
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
