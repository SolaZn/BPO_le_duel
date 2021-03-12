package Jeu;

import org.junit.Test;
import org.junit.Assert;
import java.util.LinkedList;

public class tests {

    @Test
    public void piocherVide(){
        // Vérifier que piocher si la pioche est vide est impossible.
        Joueur J = new Joueur("Cobaye");
        int nbCartes = J.getPioche().getNbCartes();
        // vider la pioche
        for (int i = 0 ; i < nbCartes ; ++i){
            J.getPioche().getCartePioche();
        }
        Assert.assertEquals(0, J.remplirMain());
    }

    @Test
    public void jouerHostile(){
        // Vérifier que le statut de jeu hostile s'applique correctement et produit une piochage approprié
        Joueur J = new Joueur("Cobaye");
        J.setJeuHostile(true);
        for(int i = 0; i < 3; ++i) {
            J.getMainJoueur().jouerCarteMain(J.getMainJoueur().getTailleMain() - 1);
        }
        Assert.assertEquals(3, J.remplirMain());
    }

    @Test
    public void jouerNonHostile(){
        // Vérifier que le statut de jeu non hostile s'applique correctement et produit une piochage approrié
        Joueur J = new Joueur("Cobaye");
        J.setJeuHostile(false);
        for(int i = 0; i < 3; ++i) {
            J.getMainJoueur().jouerCarteMain(J.getMainJoueur().getTailleMain() - 1);
        }

        Assert.assertEquals(2, J.remplirMain());
    }

    @Test
    public void joueurInitialiser(){
        //Vérifie que le joueur est bien initialiser
        Joueur J = new Joueur("Cobaye");
        Assert.assertEquals(6, J.getMainJoueur().getTailleMain());
        Assert.assertEquals(52, J.getPioche().getNbCartes());
        Assert.assertEquals(60, J.getPileDsc().showCarte());
        Assert.assertEquals(1, J.getPileAsc().showCarte());
        Assert.assertEquals(false, J.getJeuHostile());
        Assert.assertEquals(false, J.isGagnant());
    }

    @Test
    public void affichageSyntaxe(){
        // Vérifie si l'état de la main change entre deux "tours"
        Joueur J1 = new Joueur("NORD");
        Assert.assertEquals("NORD ^[01] v[60] (m6p52)", J1.toString());
        String avantTest_PILES = J1.toString();
        String avantTest_MAIN = J1.getMainJoueur().toString(J1);
        for(int i = 0; i < 3; ++i){
            J1.getMainJoueur().jouerCarteMain(J1.getMainJoueur().getTailleMain() - 1);
        }
        String apresTest_MAIN = J1.getMainJoueur().toString(J1);
        String apresTest_PILES = J1.toString();

        Assert.assertNotEquals(avantTest_MAIN,apresTest_MAIN);
        Assert.assertNotEquals(avantTest_PILES,apresTest_PILES);
    }

    @Test
    public void isEmptyMain(){
        //Vérifie que la fonction isEmpty fonctionne bien
        Joueur J = new Joueur("Cobaye");
        int nbCarte = J.getMainJoueur().getTailleMain();
        for(int i = 0; i < nbCarte; ++i){
            J.getMainJoueur().jouerCarteMain(J.getMainJoueur().getTailleMain() - 1);
        }
        Assert.assertTrue(J.getMainJoueur().isEmpty());
    }

    @Test
    public void joueurCorrect(){
        //Verifie que le joueur jouer
        Joueur J = new Joueur("Cobaye");
        Joueur J2 = new Joueur("Cobaye");
        J.getPileAsc().pushPile(21);
        J.getMainJoueur().jouerCarteMain(5);
        J.getMainJoueur().setCarteMain(11);
        LinkedList<Partie.etatCompteur> compteur = new LinkedList<>();
        Assert.assertTrue(Partie.jouer(11, "11^", J, J2, compteur));
    }

    @Test
    public void possibilitéPoserCarte(){
        //Verifie que la méthode possibilitéJouer fonctionne bien
        Joueur J = new Joueur("Cobaye");
        Joueur J2 = new Joueur("Cobaye");
        Assert.assertTrue(J.possibiliteJouer(J2));
    }

    @Test
    public void conditionVictoire(){
        //Verifie que les conditions de la victoire sont bien implémentées
        Joueur J = new Joueur("Cobaye");
        int nbCartes = J.getPioche().getNbCartes();
        for (int i = 0 ; i < nbCartes ; ++i){
            J.getPioche().getCartePioche();
        }
        int nbCartesMain = J.getMainJoueur().getTailleMain();
        for (int i = 0 ; i < nbCartesMain ; ++i){
            J.getMainJoueur().jouerCarteMain(J.getMainJoueur().getTailleMain()-1);
        }
        Assert.assertTrue(J.partieGagnee());
    }
}
