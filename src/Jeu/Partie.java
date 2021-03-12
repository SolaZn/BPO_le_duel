package Jeu;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * La classe Partie contient des méthodes permettant le déroulement d'une partie
 * de "The Game - Le Duel".
 * Elle contient ce qui est nécessaire au bon déroulement d'une partie du jeu de
 * société.
 *
 * @author Slim BEN DAALI et Anthony ZAKANI
 */
public class Partie {
    enum etatCompteur{ASC, DSC, ASCAD, DSCAD}

    /**
     *  Joue la carte sur ca pile ou la pile de l'adversaire
     * @param carteJouee La carte jouée
     * @param Carte La carte jouée plus la pile sur laquel jouée
     * @param J Le joueur qui joue
     * @param J2 l'adversaire
     * @param compteur la pile sur laquelle la carte a été jouée
     * @return vrai si la carte a pu être jouée
     */
    protected static boolean jouer(int carteJouee, String Carte, Joueur J, Joueur J2, LinkedList<etatCompteur> compteur){
        if(Carte.length() == 4){
            if(Carte.charAt(3) == '\''){
                switch (Carte.charAt(2)){
                    case 'v' :
                        if(carteJouee > J2.getPileDsc().showCarte()){
                            if (!J.chercherCarte(carteJouee))
                                return false;
                            J2.getPileDsc().pushPile(carteJouee);
                            compteur.add(etatCompteur.DSCAD);
                            J.setJeuHostile(true);
                            return true;
                        }
                        break;
                    case '^' :
                        if(carteJouee < J2.getPileAsc().showCarte()){
                            if(!(J.chercherCarte(carteJouee)))
                                return false;
                            J2.getPileAsc().pushPile(carteJouee);
                            compteur.add(etatCompteur.ASCAD);
                            J.setJeuHostile(true);
                            return true;
                        }
                        break;
                    default : return false;
                }
            }
            else
                return false;
        }
        else{
            try {
                switch (Carte.charAt(2)) {
                    case 'v':
                        if (carteJouee < J.getPileDsc().showCarte() || carteJouee == (J.getPileDsc().showCarte() + 10)) {
                            if (!(J.chercherCarte(carteJouee)))
                                return false;
                            J.getPileDsc().pushPile(carteJouee);
                            compteur.add(etatCompteur.DSC);
                            return true;
                        }
                        break;
                    case '^':
                        if (carteJouee > J.getPileAsc().showCarte() || carteJouee == (J.getPileAsc().showCarte() - 10)) {
                            if (!(J.chercherCarte(carteJouee)))
                                return false;
                            J.getPileAsc().pushPile(carteJouee);
                            compteur.add(etatCompteur.ASC);
                            return true;
                        }
                        break;
                    default:
                        return false;
                }
            }catch(StringIndexOutOfBoundsException e){
                return false;
            }
        }
        return false;
    }

    /**
     *  Effectue les opérations nécessaires au déroulement d'un tour
     * @param J désigne le joueur qui joue le tour
     * @param J2 désigne le joueur contre qui le tour est joué
     * @return vrai si le tour suivant doit se produire
     *         faux si le tour actuel est censé être le dernier
     */
    static boolean tour(Joueur J, Joueur J2){
        J.getMainJoueur().rangerMain();
        J2.getMainJoueur().rangerMain();
        // séparer pour rendre flexible ?
        if(J.getNom().equals("NORD")) {
            System.out.println(J.toString());
            System.out.println(J2.toString());
        }
        else{
            System.out.println(J2.toString());
            System.out.println(J.toString());
        }

        System.out.println(J.getMainJoueur().toString(J));
        int nbEssais = 0;
        int nbCartePoser = 0;
        int nbCartesPiochees = 0;
        boolean coupValide = false;

        if(!J.possibiliteJouer(J2)){
            J2.setGagnant();
            return false; // la partie est finie -> le jeu s'arrêtera avant le prochain tour
        }

        do {
            if (nbEssais == 0){
                System.out.print("> ");
            }
            else
                System.out.print("#> ");

            nbEssais++;

            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            String[] tab = s.split("\\s+");

            if (tab.length < 2){
                continue;
            }

            int[] intTab = new int[tab.length];
            try {
                for (int i = 0; i < tab.length; ++i) {
                    // try sinon catch sur la conversion en int (qui pourra sortir de la boucle et forcer l'utilisateur
                    // a recommencer sa saisie)
                    // try this
                    intTab[i] = Integer.parseInt(String.valueOf(tab[i].charAt(0)));
                    intTab[i] *= 10;
                    intTab[i] += Integer.parseInt(String.valueOf(tab[i].charAt(1)));
                    // or if failed, go outside and retry
                }
            }catch(NumberFormatException | StringIndexOutOfBoundsException a){
                continue;
            }

            if (!J.verifCarte(intTab)){
                continue;
            }
            LinkedList<etatCompteur> Compteur = new LinkedList<>();
            nbCartePoser = 0;
            for (int i = 0; i < intTab.length; ++i) {
                if (jouer(intTab[i], tab[i], J, J2, Compteur)) {
                    ++nbCartePoser;
                }
            }

            int nbCarteAd = 0;

            for(int i = 0; i < Compteur.size(); ++i){
                if(Compteur.get(i).equals(etatCompteur.ASCAD) || Compteur.get(i).equals(etatCompteur.DSCAD)){
                    ++nbCarteAd;
                }
            }

            if(nbCartePoser == intTab.length && nbCarteAd <= 1){
                coupValide = true;
            }
            else {
                J.reprendreCarte(J2, Compteur);
            }

            // pouvoir revenir en arrière sur les cartes en cas de jeu faux pour cause de cartes mal posées
        }while (!coupValide);

        if (!J.partieGagnee()) {
            if (!J.getPioche().isEmpty())
                nbCartesPiochees = J.remplirMain();
            J.setJeuHostile(false);
            System.out.println(nbCartePoser + " cartes posées, " + nbCartesPiochees + " cartes piochées");

            return true; // la partie n'est pas finie -> le jeu continue

        } else {
            // voir plus haut, séparer pour rendre flexible !
            if (J2.getNom().equals("NORD")) {
                System.out.println(J2.toString());
                System.out.println(J.toString());
            } else {
                System.out.println(J.toString());
                System.out.println(J2.toString());
            }

            System.out.println(J2.getMainJoueur().toString(J2));

            return false; // la partie est finie -> le jeu s'arrêtera avant le prochain tour
        }
    }

    /**
     *  Permet l'alternance des tours entre les deux joueurs
     * @param joueur1 le premier joueur à jouer
     * @param joueur2 le second joueur à jouer
     */
    public static void lancerPartie(Joueur joueur1, Joueur joueur2){
        for(;;) {
            //Nord joue
            if (joueur1.getMainJoueur().getTailleMain() >= 2) { // si sa main est en possibilité de jouer
                if (!tour(joueur1, joueur2)) { // si l'état du tour donne faux (partie perdue) -> le jeu s'interrompt
                    break;
                }
            } else {
                break;
            }

            //Sud joue
            if (joueur2.getMainJoueur().getTailleMain() >= 2) { // si sa main est en possibilité de jouer
                if (!tour(joueur2, joueur1)) { // si l'état du tour donne faux (partie perdue) -> le jeu s'interrompt
                    break;
                }
            } else {
                break;
            }
        } // Tant que l'on est pas sorti par un break
    }
}

