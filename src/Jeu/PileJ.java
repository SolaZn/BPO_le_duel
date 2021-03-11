package Jeu;

import java.util.LinkedList;

class PileJ {
    private LinkedList<Integer> pile;

    PileJ(){
        this.pile = new LinkedList<>();
    }

    void pushPile(int carte){ this.pile.push(carte); }

    int showCarte(){ return this.pile.peek(); }

    int getCarte(){
        int carte = this.pile.peek();
        this.pile.pop();
        return carte;
    }
}
