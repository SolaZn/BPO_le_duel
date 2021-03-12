package Jeu;

import Jeu.Partie.etatCompteur;
import java.util.LinkedList;

/**
 * La classe Joueur représente le joueur et toutes ses caractéristiques.
 * Ses cartes, sa façon de jouer et son état de victoire y sont représentés.
 *
 * @author Slim BEN DAALI et Anthony ZAKANI
 */
public class Joueur {
    /** Représente le nom attribué au joueur */
    private String nomJoueur;
    /** Représente la pile de cartes ascendentes du joueur */
    private PileJ pileAsc;
    /** Représente la pile de cartes descendantes du joueur */
    private PileJ pileDsc;
    /** Représente la main du joueur */
    private MainJ mainJoueur;
    /** Représente la pioche du joueur */
    private PiocheJ piocheJoueur;
    /** Représente l'état de jeu hostile du joueur
     * (si il a joué sur la pile du joueur adverse au tour
     * précédent) */
    private boolean jeuHostile;
    /** Représente l'état de victoire du joueur */
    private boolean gagnant;

    /**
     *  Initialise le joueur ainsi que tous ses attributs avec son nom
     * @param nom Le nom du joueur
     */
    public Joueur(String nom){
        this.nomJoueur = nom;
        this.piocheJoueur = new PiocheJ();
        this.mainJoueur = new MainJ(this);
        initialiserPiles();
        this.gagnant = false;
        this.jeuHostile = false;
    }

    /**
     *  Retourne le nom du joueur
     * @return le nom du joueur
     */
    public String getNom() {
        return nomJoueur;
    }

    /**
     *  Retourne l'etat de l'attribut gagnant
     * @return l'etat de l'attribut gagnant
     */
    public boolean isGagnant(){
        return this.gagnant;
    }

    /**
     *  initialise les piles ascendante et descendante
     */
    private void initialiserPiles(){
        this.pileDsc = new PileJ();
        this.pileAsc = new PileJ();

        this.pileDsc.pushPile(60);
        this.pileAsc.pushPile(1);
    }

    /**
     *  Retourne la manière d'afficher les piles
     * @return La manière d'afficher les piles
     */
    private String affichePiles(){
        String a = "^[";
        if (this.pileAsc.showCarte() < 10) {
            a += "0";
        }
        a += String.valueOf(this.pileAsc.showCarte());
        a += "] v[";
        if (this.pileDsc.showCarte() < 10) {
            a += "0";
        }
        a += String.valueOf(this.pileDsc.showCarte());
        a += "]";
        return a;
    }

    /**
     *  Retourne la pioche
     * @return la pioche
     */
    PiocheJ getPioche(){
        return this.piocheJoueur;
    }

    /**
     *  Retourne la pile ascendante du joueur
     * @return la pile ascendante
     */
    PileJ getPileAsc(){
        return this.pileAsc;
    }

    /**
     *  Retourne la pile descendante du joueur
     * @return la pile descendante
     */
    PileJ getPileDsc(){
        return this.pileDsc;
    }

    /**
     *  Retourne la main du joueur
     * @return la main
     */
    MainJ getMainJoueur(){ return this.mainJoueur; }

    /**
     *  Retourne la manière d'afficher le joueur
     * @return la manière d'afficher le joueur
     */
    public String toString() {
        return nomJoueur + " " +
                affichePiles() +
                " (m" + mainJoueur.getTailleMain()
                + "p" + piocheJoueur.getNbCartes() + ")";
    }

    /**
     *  Modifie la valeur de l'attribut gagnant a vrai
     */
    void setGagnant(){
        this.gagnant = true;
    }

    /**
     *  Modifie l'etat de l'attribut jeuHostile à Etat
     * @param Etat l'etat a mettre à l'attribut jeuHostile
     */
    void setJeuHostile(boolean Etat){
        this.jeuHostile = Etat;
    }

    /**
     *  Retourne l'etat de l'attribut jeuHostile
     * @return l'état de l'attribut jeuHostile
     */
    boolean getJeuHostile(){
        return this.jeuHostile;
    }

    /**
     *  Vérifie si les cartes jouées sont dans la main
     * @param intTab les cartes jouées
     * @return vrai si les cartes sont existantes, sinon faux
     */
    boolean verifCarte(int[] intTab){
        int cartesExistantes = 0;
        for(int cartes : intTab){
            for(int i = 0; i < this.mainJoueur.getTailleMain(); ++i){
                if (cartes == this.mainJoueur.showCarteMain(i)){
                    cartesExistantes++;
                    break;
                }
            }
        }

        return cartesExistantes == intTab.length;
    }

    /**
     *  Vérifie si le joueur peut joueur sur ces piles ou les piles de son adversaire lors de son tour
     * @param J2 L'adversaire
     * @return vrai si le joueur peut jouer sinon faux
     */
    boolean possibiliteJouer(Joueur J2) {
        int cartePossible = 0;
        for (int i = 0; i < this.mainJoueur.getTailleMain(); ++i) {
            if (this.mainJoueur.showCarteMain(i) < this.pileDsc.showCarte() || this.mainJoueur.showCarteMain(i) == this.pileDsc.showCarte() + 10) {
                ++cartePossible;
            }
            if (this.mainJoueur.showCarteMain(i) > this.pileAsc.showCarte() || this.mainJoueur.showCarteMain(i) == this.pileAsc.showCarte() - 10) {
                ++cartePossible;
            }
        }
        for (int i = 0; i < this.mainJoueur.getTailleMain(); ++i) {
            if (this.mainJoueur.showCarteMain(i) > J2.pileDsc.showCarte()) {
                ++cartePossible;
                break;
            }
            if (this.mainJoueur.showCarteMain(i) < J2.pileAsc.showCarte()) {
                ++cartePossible;
                break;
            }
        }
        return cartePossible >= 2;
    }

    /**
     *  Vérifie si le joueur a gagné la partie
     * @return vrai si le joueur a gagné sinon faux
     */
    boolean partieGagnee(){
        if(this.mainJoueur.isEmpty() && this.piocheJoueur.isEmpty()){
            this.setGagnant();
            return true;
        }
        return false;
    }

    /**
     *  Reprend les cartes posées sur ces piles ou les piles de l'adversaire lors du dernier tour
     * @param J2 L'adversaire
     * @param compteur Les lieux ou ont été posées les cartes
     */
    void reprendreCarte(Joueur J2, LinkedList<etatCompteur> compteur){
        for(int i = 0; i < compteur.size(); ++i){
            switch (compteur.get(i)){
                case ASC:
                    this.getMainJoueur().setCarteMain(this.getPileAsc().getCarte());
                    break;
                case DSC:
                    this.getMainJoueur().setCarteMain(this.getPileDsc().getCarte());
                    break;
                case ASCAD:
                    this.getMainJoueur().setCarteMain(J2.getPileAsc().getCarte());
                    break;
                case DSCAD:
                    this.getMainJoueur().setCarteMain(J2.getPileDsc().getCarte());
                    break;
            }
        }
    }

    /**
     *  Cherche la carte dans la main
     * @param carteAChercher la carte
     * @return l'index à laquel est la carte
     */
    boolean chercherCarte(int carteAChercher){
        for(int i = 0; i < this.getMainJoueur().getTailleMain(); ++i){
            if(carteAChercher == this.getMainJoueur().showCarteMain(i)){
                this.getMainJoueur().jouerCarteMain(i);
                return true;
            }
        }
        return false;
    }

    /**
     *  Rempli la main a partir de la pioche
     * @return le nombre de carte piochée
     */
    int remplirMain(){
        int nbCartesAPiocher;
        boolean aJoueAilleurs = this.getJeuHostile();
        if (aJoueAilleurs){
            nbCartesAPiocher = 6 - this.getMainJoueur().getTailleMain();
        }
        else
            nbCartesAPiocher = 2;
        if( this.getPioche().getNbCartes() >= nbCartesAPiocher) {
            for (int i = 0; i < nbCartesAPiocher; ++i) {
                this.getMainJoueur().setCarteMain(this.getPioche().getCartePioche());
            }
        }
        else if (!(this.getPioche().isEmpty())){
            nbCartesAPiocher = this.getPioche().getNbCartes();
            while (!(this.getPioche().isEmpty())) {
                this.getMainJoueur().setCarteMain(this.getPioche().getCartePioche());
            }
        }
        else{
            return 0;
        }
        return nbCartesAPiocher;
    }
}
