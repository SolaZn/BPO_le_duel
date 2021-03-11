package Jeu;

import java.util.LinkedList;
import java.util.Scanner;

public class Partie {
    enum etatCompteur{ASC, DSC, ASCAD, DSCAD}

    private boolean jouer(int carteJouee, String Carte, Joueur J, Joueur J2, LinkedList<etatCompteur> compteur){
        // ajouter les retraits de carte de la main du joueur après qu'elles aient été posées
        // + les règles de remplissage de la main en fonction de là où la carte a été posée
        if(Carte.length() == 4){
            if(Carte.charAt(3) == '\''){
                switch (Carte.charAt(2)){
                    case 'v' :
                        if(carteJouee > J2.getPileDsc().showCarte()){
                            if (!J.getMainJoueur().chercherCarte(carteJouee))
                                return false;
                            J2.getPileDsc().pushPile(carteJouee);
                            compteur.add(etatCompteur.DSCAD);
                            J.setJeuHostile(true);
                            return true;
                        }
                        break;
                    case '^' :
                        if(carteJouee < J2.getPileAsc().showCarte()){
                            if(!(J.getMainJoueur().chercherCarte(carteJouee)))
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
                            if (!(J.getMainJoueur().chercherCarte(carteJouee)))
                                return false;
                            J.getPileDsc().pushPile(carteJouee);
                            compteur.add(etatCompteur.DSC);
                            return true;
                        }
                        break;
                    case '^':
                        if (carteJouee > J.getPileAsc().showCarte() || carteJouee == (J.getPileAsc().showCarte() - 10)) {
                            if (!(J.getMainJoueur().chercherCarte(carteJouee)))
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

    public boolean tour(Joueur J, Joueur J2){
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
                nbCartesPiochees = J.getMainJoueur().remplirMain(J);
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

    public void lancerPartie(Joueur joueur1, Joueur joueur2){
        for(;;) {
            //Nord joue
            if (joueur1.getMainJoueur().getTailleMain() >= 2) {
                if (!tour(joueur1, joueur2)) { // si l'état du tour donne faux (partie perdue) -> le jeu s'interrompt
                    break;
                }
            } else {
                break;
            }

            //Sud joue
            if (joueur2.getMainJoueur().getTailleMain() >= 2) {
                if (!tour(joueur2, joueur1)) { // si l'état du tour donne faux (partie perdue) -> le jeu s'interrompt
                    break;
                }
            } else {
                break;
            }
        }
    }
}

