package Jeu;

import java.util.Collections;
import java.util.LinkedList;

class PiocheJ {
    private LinkedList<Integer> pilePioche;

    PiocheJ(){
        this.pilePioche = new LinkedList<>();
        initialiserPioche();
    }

    private void initialiserPioche(){
        for (int i = 2; i <= 59; ++i){
            this.pilePioche.push(i);
        }
        Collections.shuffle(pilePioche);
    }

    int getNbCartes(){
        return this.pilePioche.size();
    }

    boolean isEmpty(){
        return this.pilePioche.size() == 0;
    }

    int getCartePioche() {
        int carteTiree = this.pilePioche.peek();
        this.pilePioche.pop();
        return carteTiree;
    }
}
