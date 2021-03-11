package Jeu;

import java.util.Collections;
import java.util.LinkedList;

class MainJ {
    private LinkedList<Integer> main;

    MainJ(Joueur J){
        this.main = new LinkedList<>();
        for(int i = 0; i < 6; ++i) {
            this.main.add(J.getPioche().getCartePioche());
        }
    }

    void rangerMain(){
        Collections.sort(this.main);
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

    void jouerCarteMain(int idx){
        this.main.remove(idx);
    }

    boolean chercherCarte(int carteAChercher){
        for(int i = 0; i < this.main.size(); ++i){
            if(carteAChercher == this.main.get(i)){
                jouerCarteMain(i);
                return true;
            }
        }
        return false;
    }

    void setCarteMain(int carte){
        if(this.main.size() < 6) {
            this.main.add(carte);
        }
    }

    int remplirMain(Joueur J){
        int nbCartesAPiocher;
        boolean aJoueAilleurs = J.getJeuHostile();
        if (aJoueAilleurs){
            nbCartesAPiocher = 6 - this.main.size();
        }
        else
            nbCartesAPiocher = 2;
        if(J.getPioche().getNbCartes() >= nbCartesAPiocher) {
            for (int i = 0; i < nbCartesAPiocher; ++i) {
                this.main.add(J.getPioche().getCartePioche());
            }
        }
        else if (!(J.getPioche().isEmpty())){
            nbCartesAPiocher = J.getPioche().getNbCartes();
            while (!(J.getPioche().isEmpty())) {
                this.main.add(J.getPioche().getCartePioche());
            }
        }
        else{
            return 0;
        }
        return nbCartesAPiocher;
    }

    int showCarteMain(int idx){
        return this.main.get(idx);
    }

    int getTailleMain(){
        return this.main.size();
    }

    boolean isEmpty(){
        return this.main.isEmpty();
    }
}
