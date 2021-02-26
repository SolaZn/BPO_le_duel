package joueur;

import java.util.Stack;

public class PileJ {
    private Stack<Integer> pile;

    public PileJ(){
        this.pile = new Stack<>();
    }

    public void pushPile(int carte){ this.pile.push(carte); }

    public int showCarte(){ return this.pile.peek(); }

    public int getCarte(){
        int carte = this.pile.peek();
        this.pile.pop();
        return carte;
    }

    public void setPileDsc(int carte){ this.pile.push(carte); }
}
