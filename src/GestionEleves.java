import java.io.*;
import java.util.Scanner;

public class GestionEleves {

    private final Eleve[] tableauEleves;  // Tableau pour stocker les objets 'Eleve'
    private int nombreEleves;  // Compteur d'élèves ajoutés

    // Constructeur de la classe GestionEleves
    public GestionEleves(int capacite) {
        tableauEleves = new Eleve[capacite];  // Crée un tableau d'élèves avec la capacité spécifiée
        nombreEleves = 0;  // Aucun élève au départ
        chargerElevesCSV();  // Charge les élèves depuis le fichier CSV
        sauvegarderElevesCSV();  // Sauvegarde les élèves dans un fichier CSV
    }

    // Méthode d'initialisation, permet de charger les élèves depuis le fichier CSV
    public void init() {
        chargerElevesCSV();  // Appeler cette méthode dans un autre endroit si nécessaire
    }

    // Sauvegarder les élèves dans un fichier CSV
    public void sauvegarderElevesCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("eleves.csv"))) {
            // Parcourt le tableau des élèves et écrit chaque élève dans le fichier CSV
            for (int i = 0; i < nombreEleves; i++) {
                Eleve eleve = tableauEleves[i];  // Récupère l'élève à la position i
                writer.write(eleve.getNom() + "," + eleve.getPrenom() + "," + eleve.getClasse() + "," + eleve.getMoyenne());
                writer.newLine();  // Ajoute une nouvelle ligne après chaque élève
            }
        } catch (IOException e) {
            e.printStackTrace();  // Affiche une erreur si un problème survient lors de l'écriture dans le fichier
        }
    }

    // Charger les élèves depuis un fichier CSV
    public void chargerElevesCSV() {
        File file = new File("eleves.csv");  // Ouvre le fichier CSV existant
        if (!file.exists()) return;  // Si le fichier n'existe pas, on ne fait rien
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            // Lit chaque ligne du fichier CSV
            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split(",");  // Sépare chaque ligne par la virgule
                if (data.length == 4) {  // Vérifie qu'il y a bien 4 éléments (nom, prénom, classe, moyenne)
                    String nom = data[0];
                    String prenom = data[1];
                    String classe = data[2];
                    double moyenne = Double.parseDouble(data[3]);

                    Eleve eleve = new Eleve(nom, prenom, classe, moyenne);  // Crée un objet 'Eleve'
                    ajouterEleve(eleve);  // Ajoute l'élève au tableau
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // Affiche une erreur en cas de problème de lecture
        }
    }

    // Ajouter un élève dans le tableau
    public void ajouterEleve(Eleve eleve) {
        if (nombreEleves < tableauEleves.length) {  // Vérifie qu'il y a de la place dans le tableau
            tableauEleves[nombreEleves] = eleve;  // Ajoute l'élève à la position suivante du tableau
            nombreEleves++;  // Incrémente le nombre d'élèves
            sauvegarderElevesCSV();  // Sauvegarde les élèves dans le fichier CSV après l'ajout
        } else {
            System.out.println("❌ Le tableau d'élèves est plein.");  // Affiche un message si le tableau est plein
        }
    }

    // Afficher tous les élèves
    public void afficherEleves() {
        if (nombreEleves == 0) {
            System.out.println("❌ Aucun élève dans le tableau.");  // Si aucun élève n'est présent
        } else {
            System.out.println("\n📜 Liste des élèves :");
            // Affiche les détails de chaque élève
            for (int i = 0; i < nombreEleves; i++) {
                System.out.println(tableauEleves[i].toString());  // Affiche l'objet 'Eleve' à la position i
            }
        }
    }

    // Rechercher un élève par son nom
    public void rechercherEleve(String nomRecherche) {
        boolean trouve = false;
        for (int i = 0; i < nombreEleves; i++) {
            if (tableauEleves[i].getNom().equalsIgnoreCase(nomRecherche)) {  // Recherche par nom
                System.out.println(tableauEleves[i].toString());  // Affiche les détails de l'élève trouvé
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("🔍 Élève non trouvé.");  // Affiche un message si l'élève n'est pas trouvé
        }
    }

    // Supprimer un élève par sa moyenne
    public void supprimerEleveParMoyenne(double moyenneCritere) {
        for (int i = 0; i < nombreEleves; i++) {
            if (tableauEleves[i].getMoyenne() == moyenneCritere) {  // Vérifie si l'élève a la moyenne spécifiée
                // Décale tous les élèves suivants d'une position pour combler le vide
                for (int j = i; j < nombreEleves - 1; j++) {
                    tableauEleves[j] = tableauEleves[j + 1];
                }
                tableauEleves[nombreEleves - 1] = null;  // Libère la dernière case
                nombreEleves--;  // Décrémente le nombre d'élèves
                System.out.println("🗑 Élève supprimé avec succès !");
                sauvegarderElevesCSV();  // Sauvegarde les modifications dans le fichier CSV après la suppression
                return;
            }
        }
        System.out.println("❌ Aucun élève avec cette moyenne.");  // Affiche un message si aucun élève ne correspond à la moyenne
    }

    // Juger les élèves en fonction de leur moyenne
    public void jugerEleves() {
        if (nombreEleves == 0) {
            System.out.println("❌ Aucun élève à juger.");  // Si aucun élève n'est présent
            return;
        }

        System.out.println("\n=====================================");
        System.out.println("     ⚖ Jugement des Élèves ⚖      ");
        System.out.println("=====================================");

        for (int i = 0; i < nombreEleves; i++) {
            Eleve eleve = tableauEleves[i];
            double moyenne = eleve.getMoyenne();

            // Juger en fonction de la moyenne de l'élève
            if (moyenne >= 10) {
                System.out.println("✅ " + eleve.getNom() + " " + eleve.getPrenom() + " : Réussite 🎉 !");
                eleve.setClasse(passerClasseSuperieure(eleve.getClasse()));  // Passe l'élève à la classe supérieure
            } else if (moyenne < 7) {
                System.out.println("❌ " + eleve.getNom() + " " + eleve.getPrenom() + " : Échec 😞.");
            } else {
                System.out.println("⚠ " + eleve.getNom() + " " + eleve.getPrenom() + " : Se Représente 📚.");
                eleve.setClasse(passerClasseSuperieure(eleve.getClasse()));  // Passe l'élève à la classe supérieure
            }
        }

        System.out.println("=====================================");
    }

    // Méthode privée pour passer un élève à la classe supérieure
    private String passerClasseSuperieure(String classeActuelle) {
        if (classeActuelle.matches("\\d+ème année")) {  // Si la classe est sous forme d'année (ex: 1ère année)
            int annee = Integer.parseInt(classeActuelle.split("ème")[0]);  // Récupère le numéro de l'année
            return (annee + 1) + "ème année";  // Retourne l'année suivante
        }
        return classeActuelle;  // Si ce n'est pas un format d'année, on retourne la classe actuelle
    }

    // Méthodes getter pour la classe GestionEleves
    public Eleve[] getTableauEleves() {
        return tableauEleves;  // Retourne le tableau d'élèves
    }

    public int getNombreEleves() {
        return nombreEleves;  // Retourne le nombre d'élèves
    }

    // Menu interactif
    public void afficherMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int choix;

            do {
                System.out.println("\n=====================================");
                System.out.println("   🏫 Gestion des Élèves 🏫");
                System.out.println("=====================================");
                System.out.println("| 1  | 📜 Afficher les élèves    |");
                System.out.println("| 2  | ➕ Ajouter un élève       |");
                System.out.println("| 3  | 🔍 Rechercher un élève    |");
                System.out.println("| 4  | 🗑 Supprimer un élève      |");
                System.out.println("| 5  | ⚖ Juger les élèves        |");
                System.out.println("| 0  | 🚪 Quitter                |");
                System.out.println("=====================================");
                System.out.print("👉 Entrez votre choix : ");
                choix = scanner.nextInt();
                scanner.nextLine();  // Consommer la nouvelle ligne

                switch (choix) {
                    case 1 -> afficherEleves();  // Affiche tous les élèves
                    case 2 -> {
                        // Permet à l'utilisateur d'ajouter un élève
                        System.out.print("📌 Nom de l'élève : ");
                        String nom = scanner.nextLine();
                        System.out.print("📌 Prénom de l'élève : ");
                        String prenom = scanner.nextLine();
                        System.out.print("📌 Classe de l'élève : ");
                        String classe = scanner.nextLine();
                        System.out.print("📌 Moyenne de l'élève : ");
                        double moyenne = scanner.nextDouble();
                        Eleve eleve = new Eleve(nom, prenom, classe, moyenne);
                        ajouterEleve(eleve);
                    }
                    case 3 -> {
                        // Permet à l'utilisateur de rechercher un élève
                        System.out.print("🔍 Entrez le nom de l'élève à rechercher : ");
                        String nomRecherche = scanner.nextLine();
                        rechercherEleve(nomRecherche);
                    }
                    case 4 -> {
                        // Permet à l'utilisateur de supprimer un élève par moyenne
                        System.out.print("🗑 Entrez la moyenne pour suppression : ");
                        double moyenneCritere = scanner.nextDouble();
                        supprimerEleveParMoyenne(moyenneCritere);
                    }
                    case 5 -> jugerEleves();  // Juger les élèves
                    case 0 -> System.out.println("👋 Au revoir !");  // Quitter le programme
                    default -> System.out.println("⚠ Choix invalide, veuillez réessayer.");  // Message en cas de choix invalide
                }
            } while (choix != 0);  // Le menu continue tant que le choix n'est pas 0
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Erreur : " + e.getMessage());  // Gérer les erreurs d'entrée de l'utilisateur
        }
    }

    // Méthode principale pour démarrer l'application
    public static void main(String[] args) {
        GestionEleves gestion = new GestionEleves(1000);  // Crée une instance de la classe GestionEleves avec une capacité de 1000 élèves
        gestion.afficherMenu();  // Affiche le menu interactif
    }
}
