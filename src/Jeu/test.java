package Jeu;

import org.junit.Test;
import org.junit.Assert;

public class test{
    @Test
    public void piocherVide(){
        // Vérifier que piocher si la pioche est vide est impossible.
        Joueur J = new Joueur("Cobaye");
        int nbCartes = J.getPioche().getNbCartes();
        for (int i = 0 ; i < nbCartes ; ++i){
            J.getPioche().getCartePioche();
        }
        Assert.assertEquals(0, J.getMainJoueur().remplirMain(J));
    }

    @Test
    public void JouerHostile(){
        // Vérifier que le statut de jeu hostile s'applique correctement et produit une piochage
        Joueur J = new Joueur("Cobaye");
        J.setJeuHostile(true);
        for(int i = 0; i < 3; ++i) {
            J.getMainJoueur().jouerCarteMain(J.getMainJoueur().getTailleMain() - 1);
        }
        Assert.assertEquals(3, J.getMainJoueur().remplirMain(J));
    }

    @Test
    public void JouerNonHostile(){
        // Vérifier que le statut de jeu non hostile s'applique correctement et produit une piochage approrié
        Joueur J = new Joueur("Cobaye");
        J.setJeuHostile(false);
        for(int i = 0; i < 3; ++i) {
            J.getMainJoueur().jouerCarteMain(J.getMainJoueur().getTailleMain() - 1);
        }

        Assert.assertEquals(2, J.getMainJoueur().remplirMain(J));
    }

    public void joueurInitiAssert.assertEquals(2, J.getMainJoueur().remplirMain(J));
    Assert.assertEquals(2, J.getMainJoueur().robayes"";)emplirMain(J));
    }

}
