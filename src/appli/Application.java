package appli;

import joueur.Joueur;

public class Application {
    public static void main(String[] args) {
        Joueur j = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        System.out.println(j.toString());
        System.out.println(j2.toString());

        System.out.println(j.afficheMain());
    }
}
