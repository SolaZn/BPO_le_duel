package Jeu;

import Jeu.Partie.etatCompteur;
import java.util.LinkedList;

public class Joueur {
    private String nomJoueur;
    private PileJ pileAsc;
    private PileJ pileDsc;
    private MainJ mainJoueur;
    private PiocheJ piocheJoueur;
    private boolean jeuHostile;
    private boolean gagnant;

    public Joueur(String nom){
        this.nomJoueur = nom;
        this.piocheJoueur = new PiocheJ();
        this.mainJoueur = new MainJ(this);
        initialiserPiles();
        this.gagnant = false;
        this.jeuHostile = false;
    }

    public String getNom() {
        return nomJoueur;
    }

    public boolean isGagnant(){
        return this.gagnant;
    }

    private void initialiserPiles(){
        this.pileDsc = new PileJ();
        this.pileAsc = new PileJ();

        this.pileDsc.pushPile(60);
        this.pileAsc.pushPile(1);
    }

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

    PiocheJ getPioche(){
        return this.piocheJoueur;
    }
    PileJ getPileAsc(){
        return this.pileAsc;
    }
    PileJ getPileDsc(){
        return this.pileDsc;
    }
    MainJ getMainJoueur(){ return this.mainJoueur; }

    public String toString() {
        return nomJoueur + " " +
                affichePiles() +
                " (m" + mainJoueur.getTailleMain()
                + "p" + piocheJoueur.getNbCartes() + ")";
    }

    void setGagnant(){
        this.gagnant = true;
    }

    void setJeuHostile(boolean Etat){
        this.jeuHostile = Etat;
    }

    boolean getJeuHostile(){
        return this.jeuHostile;
    }

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

    boolean partieGagnee(){
        if(this.mainJoueur.isEmpty() && this.piocheJoueur.isEmpty()){
            this.setGagnant();
            return true;
        }
        return false;
    }

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
}
