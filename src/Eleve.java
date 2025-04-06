// On importe l'interface Serializable pour permettre la sérialisation de l'objet (utile si tu veux sauvegarder des objets dans un fichier, etc.)
import java.io.Serializable;

// Déclaration de la classe Eleve, qui implémente l'interface Serializable
public class Eleve implements Serializable {

   // Attributs privés de la classe Eleve
   private String nom;       // Nom de l'élève
   private String prenom;    // Prénom de l'élève
   private String classe;    // Classe de l'élève (ex: "1ère année")
   private double moyenne;   // Moyenne générale de l'élève

   // Constructeur de la classe Eleve (sert à créer un nouvel élève avec ses infos)
   public Eleve(String var1, String var2, String var3, double var4) {
      this.nom = var1;        // On assigne le nom
      this.prenom = var2;     // On assigne le prénom
      this.classe = var3;     // On assigne la classe
      this.moyenne = var4;    // On assigne la moyenne
   }

   // Getter pour récupérer le nom de l'élève
   public String getNom() {
      return this.nom;
   }

   // Setter pour modifier le nom de l'élève
   public void setNom(String var1) {
      this.nom = var1;
   }

   // Getter pour récupérer le prénom de l'élève
   public String getPrenom() {
      return this.prenom;
   }

   // Setter pour modifier le prénom de l'élève
   public void setPrenom(String var1) {
      this.prenom = var1;
   }

   // Getter pour récupérer la classe de l'élève
   public String getClasse() {
      return this.classe;
   }

   // Setter pour modifier la classe de l'élève
   public void setClasse(String var1) {
      this.classe = var1;
   }

   // Getter pour récupérer la moyenne de l'élève
   public double getMoyenne() {
      return this.moyenne;
   }

   // Setter pour modifier la moyenne de l'élève
   public void setMoyenne(double var1) {
      this.moyenne = var1;
   }

   // Méthode toString : quand on affiche un élève, c'est ce texte-là qui s'affiche
   @Override
   public String toString() {
       return nom + "  " + prenom + " - Classe : " + classe + " - Moyenne : " + moyenne;
}
}