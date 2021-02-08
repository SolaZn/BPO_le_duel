package joueur;

import java.util.LinkedList;

public class MainJ {
    private LinkedList<Carte> main;

    public MainJ(Joueur J){
        this.main = new LinkedList<>();
        for(int i = 0; i < 6; ++i) {
            this.main.add(J.getPioche().getCartePioche());
        }
    }

    public String afficheMain(Joueur J){
        StringBuilder a = new StringBuilder("cartes " + J.getNom() + " { ");
        for(int i = 0; i < 6; ++i){
            a.append(this.main.get(i).getNumero());
            a.append(" ");
        }
        a.append("}");
        return a.toString();
    }

    public Carte jouerCarteMain(int idx){
        Carte carte_jouee = this.main.get(idx);
        this.main.remove(idx);
        return carte_jouee;
    }

    public Carte showCarteMain(int idx){
        return this.main.get(idx);
    }

    public int getTailleMain(){
        return this.main.size();
    }
}
