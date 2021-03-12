package Jeu;

import java.util.Collections;
import java.util.LinkedList;

/**
 * La classe MainJ représente la main (les cartes que peut jouer un joueur).
 * Y sont représentés sa contenance et les manières de la manipuler.
 *
 * @author Slim BEN DAALI et Anthony ZAKANI
 */
class MainJ {
    /** Représente une liste chaînée d'entiers correspondants
     * aux cartes de la main */
    private LinkedList<Integer> main;

    /**
     * @brief Initialise la main en la remplissant de six cartes de la pioche
     * @param J Le joueur sur lequel on
     */
    MainJ(Joueur J){
        this.main = new LinkedList<>();
        for(int i = 0; i < 6; ++i) {
            this.main.add(J.getPioche().getCartePioche());
        }
    }

    /**
     * @brief Range la main dans l'ordre croissant
     */
    void rangerMain(){
        Collections.sort(this.main);
    }

    /**
     * @brief Retourne la manière d'afficher la main du joueur
     * @param J le joueur
     * @return la manière d'afficher la main
     */
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

    /**
     * @brief Supprime la carte de la main
     * @param idx L'index de la carte dans la main
     */
    void jouerCarteMain(int idx){
        this.main.remove(idx);
    }

    /**
     * @brief Ajoute la carte dans la main
     * @param carte la carte
     */
    void setCarteMain(int carte){
        if(this.main.size() < 6) {
            this.main.add(carte);
        }
    }

    /**
     * @brief Retourne le numéro de la carte à l'index dans la main
     * @param idx L'index
     * @return La carte
     */
    int showCarteMain(int idx){
        return this.main.get(idx);
    }

    /**
     * @brief Retourne la taille de la main
     * @return la taille de la main
     */
    int getTailleMain(){
        return this.main.size();
    }

    /**
     * @brief Retourne si la carte est vide
     * @return vrai si la carte est vide sinon faux
     */
    boolean isEmpty(){
        return this.main.isEmpty();
    }
}
