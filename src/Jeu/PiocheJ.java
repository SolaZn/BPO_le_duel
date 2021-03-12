package Jeu;

import java.util.Collections;
import java.util.LinkedList;

/**
 * La classe PiocheJ représente la pioche qu'un joueur peut utiliser pour piocher ses cartes.
 * Y sont représentés sa contenance et les manières de la manipuler.
 *
 * @author Slim BEN DAALI et Anthony ZAKANI
 */
class PiocheJ {
    /** Représente la liste chaînée d'entiers correspondant
     * aux cartes dans la pioche du joueur */
    private LinkedList<Integer> pilePioche;

    /**
     * @brief Initialise la pioche
     */
    PiocheJ(){
        this.pilePioche = new LinkedList<>();
        initialiserPioche();
    }

    /**
     * Remplie la pioche de 58 cartes
     */
    private void initialiserPioche(){
        for (int i = 2; i <= 59; ++i){
            this.pilePioche.push(i);
        }
        Collections.shuffle(pilePioche);
    }

    /**
     * @brief Retourne le nombre de carte dans la pioche
     * @return le nombre de carte dans la pioche
     */
    int getNbCartes(){
        return this.pilePioche.size();
    }

    /**
     * @brief Retourne si la pioche est vide
     * @return vrai si la pioche est vide sinon faux
     */
    boolean isEmpty(){
        return this.pilePioche.size() == 0;
    }

    /**
     * @brief Retourne le numéro de la carte en tête et la supprime
     * @return le numéro de la carte en tête
     */
    int getCartePioche() {
        int carteTiree = this.pilePioche.peek();
        this.pilePioche.pop();
        return carteTiree;
    }
}
