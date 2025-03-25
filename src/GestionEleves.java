import java.io.*;
import java.util.Scanner;

public class GestionEleves {

    private final Eleve[] tableauEleves;
    private int nombreEleves;

    // Constructeur
   public GestionEleves(int capacite) {
    tableauEleves = new Eleve[capacite];
    nombreEleves = 0;
    chargerElevesCSV();
    sauvegarderElevesCSV();
}

public void init() {
    chargerElevesCSV();  // Appeler cette méthode dans un autre endroit
}

    // Sauvegarder les élèves dans un fichier CSV
    public void sauvegarderElevesCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("eleves.csv"))) {
            for (int i = 0; i < nombreEleves; i++) {
                Eleve eleve = tableauEleves[i];
                writer.write(eleve.getNom() + "," + eleve.getPrenom() + "," + eleve.getClasse() + "," + eleve.getMoyenne());
                writer.newLine(); // Pour chaque élève, on ajoute une nouvelle ligne
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Charger les élèves depuis un fichier CSV
    public void chargerElevesCSV() {
        File file = new File("eleves.csv");  // Ton fichier CSV existant
        if (!file.exists()) return;  // Si le fichier n'existe pas, on ne fait rien
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split(",");  // Séparer les données par une virgule
                if (data.length == 4) {
                    String nom = data[0];
                    String prenom = data[1];
                    String classe = data[2];
                    double moyenne = Double.parseDouble(data[3]);
    
                    Eleve eleve = new Eleve(nom, prenom, classe, moyenne);
                    ajouterEleve(eleve);  // Ajouter l'élève au tableau
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ajouter un élève
    public void ajouterEleve(Eleve eleve) {
        if (nombreEleves < tableauEleves.length) {
            tableauEleves[nombreEleves] = eleve;
            nombreEleves++;
            sauvegarderElevesCSV();  // Sauvegarde après chaque ajout
        } else {
            System.out.println("❌ Le tableau d'élèves est plein.");
        }
    }

    // Afficher tous les élèves
    public void afficherEleves() {
        if (nombreEleves == 0) {
            System.out.println("❌ Aucun élève dans le tableau.");
        } else {
            System.out.println("\n📜 Liste des élèves :");
            for (int i = 0; i < nombreEleves; i++) {
                System.out.println(tableauEleves[i].toString());
            }
        }
    }

    // Rechercher un élève par son nom
    public void rechercherEleve(String nomRecherche) {
        boolean trouve = false;
        for (int i = 0; i < nombreEleves; i++) {
            if (tableauEleves[i].getNom().equalsIgnoreCase(nomRecherche)) {
                System.out.println(tableauEleves[i].toString());
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("🔍 Élève non trouvé.");
        }
    }

    // Supprimer un élève par moyenne
    public void supprimerEleveParMoyenne(double moyenneCritere) {
        for (int i = 0; i < nombreEleves; i++) {
            if (tableauEleves[i].getMoyenne() == moyenneCritere) {
                for (int j = i; j < nombreEleves - 1; j++) {
                    tableauEleves[j] = tableauEleves[j + 1];
                }
                tableauEleves[nombreEleves - 1] = null;
                nombreEleves--;
                System.out.println("🗑 Élève supprimé avec succès !");
                sauvegarderElevesCSV();  // Sauvegarder les élèves après suppression dans le fichier CSV
                return;
            }
        }
        System.out.println("❌ Aucun élève avec cette moyenne.");
    }

    // Juger les élèves
    public void jugerEleves() {
        if (nombreEleves == 0) {
            System.out.println("❌ Aucun élève à juger.");
            return;
        }

        System.out.println("\n=====================================");
        System.out.println("     ⚖ Jugement des Élèves ⚖      ");
        System.out.println("=====================================");

        for (int i = 0; i < nombreEleves; i++) {
            Eleve eleve = tableauEleves[i];
            double moyenne = eleve.getMoyenne();

            if (moyenne >= 10) {
                System.out.println("✅ " + eleve.getNom() + " " + eleve.getPrenom() + " : Réussite 🎉 !");
                eleve.setClasse(passerClasseSuperieure(eleve.getClasse()));
            } else if (moyenne < 7) {
                System.out.println("❌ " + eleve.getNom() + " " + eleve.getPrenom() + " : Échec 😞.");
            } else {
                System.out.println("⚠ " + eleve.getNom() + " " + eleve.getPrenom() + " : Se Représente 📚.");
                eleve.setClasse(passerClasseSuperieure(eleve.getClasse()));
            }
        }

        System.out.println("=====================================");
    }

    // Passer en classe supérieure
    private String passerClasseSuperieure(String classeActuelle) {
        if (classeActuelle.matches("\\d+ème année")) {
            int annee = Integer.parseInt(classeActuelle.split("ème")[0]);
            return (annee + 1) + "ème année";
        }
        return classeActuelle;
    }

    // Méthodes getter pour la classe GestionEleves
    public Eleve[] getTableauEleves() {
        return tableauEleves;
    }

    public int getNombreEleves() {
        return nombreEleves;
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
                scanner.nextLine(); // Consommer la nouvelle ligne

                switch (choix) {
                    case 1 -> afficherEleves();
                    case 2 -> {
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
                        System.out.print("🔍 Entrez le nom de l'élève à rechercher : ");
                        String nomRecherche = scanner.nextLine();
                        rechercherEleve(nomRecherche);
                    }
                    case 4 -> {
                        System.out.print("🗑 Entrez la moyenne pour suppression : ");
                        double moyenneCritere = scanner.nextDouble();
                        supprimerEleveParMoyenne(moyenneCritere);
                    }
                    case 5 -> jugerEleves();
                    case 0 -> System.out.println("👋 Au revoir !");
                    default -> System.out.println("⚠ Choix invalide, veuillez réessayer.");
                }
            } while (choix != 0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GestionEleves gestion = new GestionEleves(1000);
        gestion.afficherMenu();
    }
}
