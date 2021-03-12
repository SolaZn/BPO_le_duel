package Jeu;

import java.util.LinkedList;

/**
 * La classe PileJ représente la pile de cartes sur laquelle un joueur peut jouer.
 * Y sont représentés sa contenance et les manières de la manipuler.
 *
 * @author Slim BEN DAALI et Anthony ZAKANI
 */
class PileJ {
    private LinkedList<Integer> pile;

    /**
     * @brief Initialise la pile
     */
    PileJ(){
        this.pile = new LinkedList<>();
    }

    /**
     * @brief Ajoute la carte à la pile
     * @param carte la carte
     */
    void pushPile(int carte){ this.pile.push(carte); }

    /**
     * @brief Retourne le numéro de la carte
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
