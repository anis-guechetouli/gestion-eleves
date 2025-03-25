import java.io.Serializable;

public class Eleve implements Serializable {

    private String nom;
    private String prenom;
    private String classe;
    private double moyenne;

    // Constructeur
    public Eleve(String nom, String prenom, String classe, double moyenne) {
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.moyenne = moyenne;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " - Classe : " + classe + " - Moyenne : " + moyenne;
    }
}
