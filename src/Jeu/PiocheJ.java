package Jeu;

import java.util.Collections;
import java.util.Stack;

class PiocheJ {
    private Stack<Integer> pilePioche;
    private int nbCartes;

    PiocheJ(){
        this.pilePioche = new Stack<>();
        this.nbCartes = 0;
        initialiserPioche();
    }

    private void initialiserPioche(){
        for (int i = 2; i <= 59; ++i){
            this.pilePioche.push(i);
            this.nbCartes++;
        }
        Collections.shuffle(pilePioche);
    }

    int getNbCartes(){
        return this.nbCartes;
    }

    boolean isEmpty(){
        return this.nbCartes == 0;
    }

    int getCartePioche() {
        int carteTiree = this.pilePioche.peek();
        this.pilePioche.pop();
        this.nbCartes--;
        return carteTiree;
    }
}
