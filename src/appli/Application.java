/*
  BPO Projet période C : Projet The Game – Le Duel
  Auteurs : Slim BEN DAALI et Anthony ZAKANI
   dans le cadre d'un DUT Informatique au sein de l'Université de Paris
  Dernière mise à jour : vendredi 12 mars 2021 à 23h40
 */

package appli;

import Jeu.Joueur;
import Jeu.Partie;

/**
 * La classe Application contient le main ainsi que les messages
 * de victoire/défaite.
 * Elle appelle le lancement d'une partie.
 *
 * @author Slim BEN DAALI et Anthony ZAKANI
 */
public class Application {
    public static void main(String[] args) {
        Joueur j = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        Partie.lancerPartie(j,j2);

        if(j.isGagnant()){
            System.out.println("partie finie, " + j.getNom() + " a gagné");
        }else
            System.out.println("partie finie, " + j2.getNom() + " a gagné");
    }
}