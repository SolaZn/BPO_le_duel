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
                            if (!J.getMainJoueur().chercherCarte(carteJouee))
                                return false;
                            J2.getPileDsc().pushPile(carteJouee);
                            compteur.add(etatCompteur.DscAd);
                            J.setJeuHostile(true);
                            return true;
                        }
                        break;
                    case '^' :
                        if(carteJouee < J2.getPileAsc().showCarte()){
                            if(!(J.getMainJoueur().chercherCarte(carteJouee)))
                                return false;
                            J2.getPileAsc().pushPile(carteJouee);
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
                    if(carteJouee < J.getPileDsc().showCarte() || carteJouee == (J.getPileDsc().showCarte() + 10)){
                        if(!(J.getMainJoueur().chercherCarte(carteJouee)))
                            return false;
                        J.getPileDsc().pushPile(carteJouee);
                        compteur.add(etatCompteur.Dsc);
                        return true;
                    }
                    break;
                case '^' :
                    if(carteJouee > J.getPileAsc().showCarte() || carteJouee == (J.getPileAsc().showCarte() - 10)){
                        if(!(J.getMainJoueur().chercherCarte(carteJouee)))
                            return false;
                        J.getPileAsc().pushPile(carteJouee);
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

    private static boolean tour(Joueur J, Joueur J2){
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

        if(!possibiliteJouer(J, J2)){
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
            }catch(NumberFormatException e){
                continue;
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
            nbCartePoser = 0;
            for (int i = 0; i < intTab.length; ++i) {
                if (jouer(intTab[i], tab[i], J, J2, Compteur)) {
                    ++nbCartePoser;
                }
            }

            int nbCarteAd = 0;

            for(int i = 0; i < Compteur.size(); ++i){
                if(Compteur.get(i).equals(etatCompteur.AscAd) || Compteur.get(i).equals(etatCompteur.DscAd)){
                    ++nbCarteAd;
                }
            }

            if(nbCartePoser == intTab.length && nbCarteAd <= 1){
                coupValide = true;
            }
            else {
                reinitialiserTour(J, J2, Compteur);
            }

            // pouvoir revenir en arrière sur les cartes en cas de jeu faux pour cause de cartes mal posées
        }while (!coupValide);

        if (!partieGagnee(J)) {
            if (!J.getPioche().isEmpty())
                nbCartesPiochees = J.getMainJoueur().remplirMain(J);
            J.setJeuHostile(false);
            System.out.println(nbCartePoser + " cartes posées, " + nbCartesPiochees + " cartes piochées");

            return true; // la partie n'est pas finie -> le jeu continue

        } else {
            // voir plus haut, séparer pour rendre flexible !
            if (J2.getNom().equals("NORD")) {
                System.out.println(J.toString());
                System.out.println(J2.toString());
            } else {
                System.out.println(J2.toString());
                System.out.println(J.toString());
            }

            System.out.println(J2.getMainJoueur().toString(J2));

            return false; // la partie est finie -> le jeu s'arrêtera avant le prochain tour
        }
    }

    private static boolean possibiliteJouer(Joueur J, Joueur J2) {
        int cartePossible = 0;
        for (int i = 0; i < J.getMainJoueur().getTailleMain(); ++i) {
            if (J.getMainJoueur().showCarteMain(i) < J.getPileDsc().showCarte() || J.getMainJoueur().showCarteMain(i) == J.getPileDsc().showCarte() + 10) {
                ++cartePossible;
            }
            if (J.getMainJoueur().showCarteMain(i) > J.getPileAsc().showCarte() || J.getMainJoueur().showCarteMain(i) == J.getPileAsc().showCarte() - 10) {
                ++cartePossible;
            }
        }
        for (int i = 0; i < J.getMainJoueur().getTailleMain(); ++i) {
            if (J.getMainJoueur().showCarteMain(i) > J2.getPileDsc().showCarte()) {
                ++cartePossible;
                break;
            }
            if (J.getMainJoueur().showCarteMain(i) < J2.getPileAsc().showCarte()) {
                ++cartePossible;
                break;
            }
        }
        return cartePossible >= 2;
    }

    private static boolean partieGagnee(Joueur J){
        if(J.getMainJoueur().isEmpty() && J.getPioche().isEmpty()){
            J.setGagnant();
            return true;
        }
        // AJOUTER LES CAS OU LE JOUEUR NE PEUT PLUS JOUER
        return false;
    }

    public static void main(String[] args) {
        Joueur j = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        for(;;){
            //Nord joue
            if (j.getMainJoueur().getTailleMain() >= 2) {
                if (!tour(j, j2)){ // si l'état du tour donne faux (partie perdue) -> le jeu s'interrompt
                    break;
                }
            } else {
                break;
            }

            //Sud joue
            if (j2.getMainJoueur().getTailleMain() >= 2) {
                if (!tour(j2, j)){ // si l'état du tour donne faux (partie perdue) -> le jeu s'interrompt
                    break;
                }
            }
            else {
                break;
            }
        }

        if(j.isGagnant()){
            System.out.println("partie finie, " + j.getNom() + " a gagné");
        }else
            System.out.println("partie finie, " + j2.getNom() + " a gagné");
    }
}