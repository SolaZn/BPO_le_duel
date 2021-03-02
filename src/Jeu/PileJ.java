package Jeu;

import java.util.Stack;

class PileJ {
    private Stack<Integer> pile;

    PileJ(){
        this.pile = new Stack<>();
    }

    void pushPile(int carte){ this.pile.push(carte); }

    int showCarte(){ return this.pile.peek(); }

    int getCarte(){
        int carte = this.pile.peek();
        this.pile.pop();
        return carte;
    }
}
