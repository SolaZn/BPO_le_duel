package Jeu;

import java.util.LinkedList;

/**
 * La classe PileJ représente la pile de cartes sur laquelle un joueur peut jouer.
 * Y sont représentés sa contenance et les manières de la manipuler.
 *
 * @author Slim BEN DAALI et Anthony ZAKANI
 */
class PileJ {
    /** Représente la liste chaînée d'entiers correspondant aux
     * cartes dans la pile du joueur */
    private LinkedList<Integer> pile;

    /**
     *  Initialise la pile
     */
    PileJ(){
        this.pile = new LinkedList<>();
    }

    /**
     *  Ajoute la carte à la pile
     * @param carte la carte
     */
    void pushPile(int carte){ this.pile.push(carte); }

    /**
     *  Retourne le numéro de la carte
     * @return le numéro de la carte
     */
    int showCarte(){ return this.pile.peek(); }

    /**
     * brief Retourne le numéro de la carte et la supprime
     * @return le numéro de la carte
     */
    int getCarte(){
        int carte = this.pile.peek();
        this.pile.pop();
        return carte;
    }
}
