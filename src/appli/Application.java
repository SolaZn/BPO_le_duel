package appli;

import Jeu.Joueur;
import Jeu.Partie;

public class Application {
    public static void main(String[] args) {
        Joueur j = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        Partie partieDuel = new Partie();

        partieDuel.lancerPartie(j,j2);

        if(j.isGagnant()){
            System.out.println("partie finie, " + j.getNom() + " a gagné");
        }else
            System.out.println("partie finie, " + j2.getNom() + " a gagné");
    }
}