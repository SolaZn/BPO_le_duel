package joueur;

import java.util.Collections;
import java.util.Stack;

public class Pioche {
    private Stack<Carte> pilePioche;
    private int nbCartes;

    public Pioche(){
        this.pilePioche = new Stack<>();
        this.nbCartes = 0;
        initialiserPioche();
    }

    private void initialiserPioche(){
        for (int i = 2; i <= 59; ++i){
            this.pilePioche.push(new Carte(i));
            this.nbCartes++;
        }
        Collections.shuffle(pilePioche);
    }

    public int getNbCartes(){
        return this.nbCartes;
    }

    public Carte getCartePioche() {
        Carte carte_tiree = this.pilePioche.peek();
        this.pilePioche.pop();
        this.nbCartes--;
        return carte_tiree;
    }
}
