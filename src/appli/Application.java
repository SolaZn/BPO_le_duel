package appli;

import joueur.Joueur;

import java.util.LinkedList;
import java.util.Scanner;

public class Application {
    enum etatCompteur{Asc, Dsc, AscAd, DscAd}

    private static boolean jouer(int carteJouee, String Carte, Joueur J, Joueur J2, LinkedList<etatCompteur> compteur){
        // ajouter les retraits de carte de la main du joueur après qu'elles aient été posées
        // + les règles de remplissage de la main en fonction de là où la carte a été posée
        if(Carte.length() == 4){
            if(Carte.charAt(3) == '\''){
                switch (Carte.charAt(2)){
                    case 'v' :
                        if(carteJouee > J2.getPileDsc().showCarte()){
                            J2.getPileDsc().pushPile(carteJouee);
                            J.getMainJoueur().chercherCarte(carteJouee);
                            compteur.add(etatCompteur.DscAd);
                            J.setJeuHostile(true);
                            return true;
                        }
                        break;
                    case '^' :
                        if(carteJouee < J2.getPileAsc().showCarte()){
                            J2.getPileAsc().pushPile(carteJouee);
                            J.getMainJoueur().chercherCarte(carteJouee);
                            compteur.add(etatCompteur.AscAd);
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
            switch (Carte.charAt(2)){
                case 'v' :
                    if(carteJouee < J.getPileDsc().showCarte() || carteJouee == (J.getPileDsc().showCarte() - 10)){
                        J.getPileDsc().pushPile(carteJouee);
                        J.getMainJoueur().chercherCarte(carteJouee);
                        compteur.add(etatCompteur.Dsc);
                        return true;
                    }
                    break;
                case '^' :
                    if(carteJouee > J.getPileAsc().showCarte() || carteJouee == (J.getPileAsc().showCarte() + 10)){
                        J.getPileAsc().pushPile(carteJouee);
                        J.getMainJoueur().chercherCarte(carteJouee);
                        compteur.add(etatCompteur.Asc);
                        return true;
                    }
                    break;
                default : return false;
            }
        }
        return false;
    }

    private static boolean verifCarte(int[] intTab, Joueur J){
        int cartesExistantes = 0;
        for(int cartes : intTab){
            for(int i = 0; i < J.getMainJoueur().getTailleMain(); ++i){
                if (cartes == J.getMainJoueur().showCarteMain(i)){
                    cartesExistantes++;
                    break;
                }
            }
        }

        return cartesExistantes == intTab.length;
    }

    private static void reinitialiserTour(Joueur J, Joueur J2, LinkedList<etatCompteur> compteur){
        for(int i = 0; i < compteur.size(); ++i){
            switch (compteur.get(i)){
                case Asc:
                    J.getMainJoueur().setCarteMain(J.getPileAsc().getCarte());
                    break;
                case Dsc:
                    J.getMainJoueur().setCarteMain(J.getPileDsc().getCarte());
                    break;
                case AscAd:
                    J.getMainJoueur().setCarteMain(J2.getPileAsc().getCarte());
                    break;
                case DscAd:
                    J.getMainJoueur().setCarteMain(J2.getPileDsc().getCarte());
                    break;
            }
        }
    }

    private static void tour(Joueur J, Joueur J2){
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
        boolean coupValide = false;

        do {
            if (nbEssais == 0){
                System.out.print("> ");
            }
            else
                System.out.print("#> ");

            nbEssais++;

            // separer
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            String[] tab = s.split("\\s+");

            if (tab.length < 2){
                continue;
            }

            int[] intTab = new int[tab.length];

            for (int i = 0; i < tab.length; ++i) {
                // try sinon catch sur la conversion en int (qui pourra sortir de la boucle et forcer l'utilisateur
                // a recommencer sa saisie)
                // try this
                intTab[i] = Integer.parseInt(String.valueOf(tab[i].charAt(0)));
                intTab[i] *= 10;
                intTab[i] += Integer.parseInt(String.valueOf(tab[i].charAt(1)));
                // or if failed, go outside and retry
            }

            if (!verifCarte(intTab, J)){
                continue;
            }
                /*(boolean) jouer(intTab[i], tab[i], J, J2);
                    //-> créé une carte 12
                    //-> pas besoin de vérifier l'existence de la carte
                    //-> on traite le troisième et eventuellement quatrième caractère si il existe
                    //-> en fonction des  caractères lu (if/switch), alo
                        si "^`"; si "^"; si "v"; si "v`"; default : faux;
                            alors on verifie là -> faux;
                                alors on joue et on modifie
                                    alors on l'enlève soit de J soit de J2' et on la remets etc...*/
            LinkedList<etatCompteur> Compteur = new LinkedList<>();
            int nbIterations = 0;
            for(int i = 0; i < intTab.length; ++i){
                if(jouer(intTab[i], tab[i], J, J2, Compteur)) {
                    ++nbIterations;
                }
            }

            int nbCarteAd = 0;

            for(int i = 0; i < Compteur.size(); ++i){
                if(Compteur.get(i).equals(etatCompteur.AscAd) || Compteur.get(i).equals(etatCompteur.DscAd)){
                    ++nbCarteAd;
                }
            }

            if(nbIterations == intTab.length && nbCarteAd <= 1){
                coupValide = true;
            }
            else {
                reinitialiserTour(J, J2, Compteur);
            }

            // pouvoir revenir en arrière sur les cartes en cas de jeu faux pour cause de cartes mal posées
        }while (!coupValide);
    }

    private static boolean partieGagnee(Joueur J){
        if(J.getMainJoueur().isEmpty() && J.getPioche().isEmpty()){
            J.setGagnant();
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Joueur j = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        for(;;){
            //Nord joue
            if (j.getMainJoueur().getTailleMain() >= 2) {
                tour(j, j2);
                if (partieGagnee(j)) { // partiefinie vérifie à la fois la victoire de J1
                    break;
                }
                if (!j.getPioche().isEmpty())
                    j.getMainJoueur().remplirMain(j);
                j.setJeuHostile(false);
            } else {

                break;
            }

            //Sud joue
            if (j2.getMainJoueur().getTailleMain() >= 2) {
                tour(j2, j);
                if (partieGagnee(j2)) { // partiefinie vérifie à la fois la victoire de J2
                    break;
                }
                if (!j2.getPioche().isEmpty())
                    j2.getMainJoueur().remplirMain(j2);
                j2.setJeuHostile(false);
            } else {
                break;
            }
        }

        if(j.isGagnant()){
            System.out.println(j.getNom() + " est le gagnant de la partie !");
        }else
            System.out.println(j2.getNom() + " est le gagnant de la partie !");
    }
}