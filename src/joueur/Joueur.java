package joueur;

import java.util.LinkedList;
import java.util.Stack;

public class Joueur {
    private String nomJoueur;
    private Stack<Carte> pileAsc;
    private Stack<Carte> pileDsc;
    private LinkedList<Carte> mainJoueur;
    private Pioche piocheJoueur;

    public Joueur(String nom){
        this.nomJoueur = nom;
        this.piocheJoueur = new Pioche();
        initialiserMain();
        initialiserPiles();
    }

    private void initialiserMain(){
        this.mainJoueur = new LinkedList<>();
        for(int i = 0; i < 6; ++i) {
            this.mainJoueur.add(piocheJoueur.getCarte());
        }
    }

    public String afficheMain(){
        String a = "cartes " + this.nomJoueur + " { ";
        for(int i = 0; i < 6; ++i){
            a += String.valueOf(this.mainJoueur.get(i).getNumero());
            a += " ";
        }
        a += "}";
        return a;
    }

    // Coder la fonction qui permettra de jouer une carte provenant de la main
    // Il faudra pouvoir la retirer de la LinkedList (s'insprier de getCarte() et
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

    public String toString() {
        return nomJoueur + " " +
                affichePiles() +
                " (m" + mainJoueur.size()
                + "p" + piocheJoueur.getNbCartes() + ")";
    }
}
