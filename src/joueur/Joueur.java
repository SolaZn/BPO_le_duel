package joueur;

import java.util.Stack;

public class Joueur {
    private String nomJoueur;
    private PileJ pileAsc;
    private PileJ pileDsc;
    private MainJ mainJoueur;
    private Pioche piocheJoueur;
    private boolean jeuHostile;
    private boolean gagnant;

    public Joueur(String nom){
        this.nomJoueur = nom;
        this.piocheJoueur = new Pioche();
        this.mainJoueur = new MainJ(this);
        initialiserPiles();
        this.gagnant = false;
        this.jeuHostile = false;
    }

    public String getNom() {
        return nomJoueur;
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

    public Pioche getPioche(){
        return this.piocheJoueur;
    }
    public PileJ getPileAsc(){
        return this.pileAsc;
    }
    public PileJ getPileDsc(){
        return this.pileDsc;
    }
    public MainJ getMainJoueur(){ return this.mainJoueur; }

    public String toString() {
        return nomJoueur + " " +
                affichePiles() +
                " (m" + mainJoueur.getTailleMain()
                + "p" + piocheJoueur.getNbCartes() + ")";
    }

    public void setGagnant(){
        this.gagnant = true;
    }

    public void setJeuHostile(boolean Etat){
        this.jeuHostile = Etat;
    }

    public boolean getJeuHostile(){
        return this.jeuHostile;
    }

    public boolean isGagnant(){
        return this.gagnant;
    }
}
