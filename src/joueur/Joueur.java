package joueur;

import java.util.Stack;

public class Joueur {
    private String nomJoueur;
    private Stack<Carte> pileAsc;
    private Stack<Carte> pileDsc;
    private MainJ mainJoueur;
    private Pioche piocheJoueur;

    // ajouter un champ Gagnant pour pouvoir le changer et le verifier en fin de partie

    public Joueur(String nom){
        this.nomJoueur = nom;
        this.piocheJoueur = new Pioche();
        this.mainJoueur = new MainJ(this);
        initialiserPiles();
    }

    // séparer la main du joueur

    public String getNom() {
        return nomJoueur;
    }

    public Carte showLastAsc(){
        return this.pileAsc.peek();
    }
    public Carte showLastDsc(){
        return this.pileDsc.peek();
    }


    // fin main

    // Coder la fonction qui permettra de jouer une carte provenant de la main
    // Il faudra pouvoir la retirer de la LinkedList (s'insprier de getCartePioche() et
    // de la méthode move/delete? dans LinkedList. Et pouvoir la remettre en cas d'échec du tour

    private void initialiserPiles(){
        this.pileDsc = new Stack<>();
        this.pileAsc = new Stack<>();

        this.pileDsc.push(new Carte(60));
        this.pileAsc.push(new Carte(1));
    }

    private String affichePiles(){
        String a = "^[";
        a += String.valueOf(this.pileAsc.peek().getNumero());
        a += "] v[";
        a += String.valueOf(this.pileDsc.peek().getNumero());
        a += "]";

        return a;
    }

    public void setPileDsc(int numero){
        Carte carte = new Carte(numero);
        this.pileDsc.push(carte);
    }
    public void setPileAsc(int numero){
        Carte carte = new Carte(numero);
        this.pileAsc.push(carte);
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
