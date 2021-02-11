package joueur;

import java.util.Stack;

public class Joueur {
    private String nomJoueur;
    private Stack<Integer> pileAsc;
    private Stack<Integer> pileDsc;
    private MainJ mainJoueur;
    private Pioche piocheJoueur;
    private boolean gagnant;

    // ajouter un champ Gagnant pour pouvoir le changer et le verifier en fin de partie

    public Joueur(String nom){
        this.nomJoueur = nom;
        this.piocheJoueur = new Pioche();
        this.mainJoueur = new MainJ(this);
        initialiserPiles();
        this.gagnant = false;
    }

    // séparer la main du joueur

    public String getNom() {
        return nomJoueur;
    }

    public int showLastAsc(){
        return this.pileAsc.peek();
    }
    public int showLastDsc(){
        return this.pileDsc.peek();
    }


    // fin main

    // Coder la fonction qui permettra de jouer une carte provenant de la main
    // Il faudra pouvoir la retirer de la LinkedList (s'insprier de getCartePioche() et
    // de la méthode move/delete? dans LinkedList. Et pouvoir la remettre en cas d'échec du tour

    private void initialiserPiles(){
        this.pileDsc = new Stack<>();
        this.pileAsc = new Stack<>();

        this.pileDsc.push(60);
        this.pileAsc.push(1);
    }

    private String affichePiles(){
        String a = "^[";
        a += String.valueOf(this.pileAsc.peek());
        a += "] v[";
        a += String.valueOf(this.pileDsc.peek());
        a += "]";

        return a;
    }

    public void setPileDsc(int numero){
        this.pileDsc.push(numero);
    }
    public int getPileDsc(){
        int carte = this.pileDsc.peek();
        this.pileDsc.pop();
        return carte;
    }
    public void setPileAsc(int numero){
        this.pileAsc.push(numero);
    }
    public int getPileAsc(){
        int carte = this.pileAsc.peek();
        this.pileAsc.pop();
        return carte;
    }

    public Pioche getPioche(){
        return this.piocheJoueur;
    }

    public MainJ getMainJoueur(){
        return this.mainJoueur;
    }

    public String toString() {
        return nomJoueur + " " +
                affichePiles() +
                " (m" + mainJoueur.getTailleMain()
                + "p" + piocheJoueur.getNbCartes() + ")";
    }
}
