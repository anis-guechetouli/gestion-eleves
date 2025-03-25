import java.awt.*;
import javax.swing.*;

public class GestionElevesGUI {
    private final GestionEleves gestion;
    private JFrame frame;

    public GestionElevesGUI() {
        gestion = new GestionEleves(1000);
        initialiserUI();
    }

    private void initialiserUI() {
        // Création de la fenêtre principale
        frame = new JFrame("🏫 Gestion des Élèves");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        // Création des boutons
        JButton btnAfficher = new JButton("📜 Afficher les élèves");
        JButton btnAjouter = new JButton("➕ Ajouter un élève");
        JButton btnRechercher = new JButton("🔍 Rechercher un élève");
        JButton btnSupprimer = new JButton("🗑 Supprimer un élève");
        JButton btnjuger = new JButton("⚖ Juger les élèves");
        JButton btnQuitter = new JButton("🚪 Quitter");

        // Ajout des actions aux boutons
        btnAfficher.addActionListener(_ -> afficherEleves());
        btnAjouter.addActionListener(_ -> ajouterEleve());
        btnRechercher.addActionListener(_ -> rechercherEleve());
        btnSupprimer.addActionListener(_ -> supprimerEleve());
        btnjuger.addActionListener(_ -> jugerEleves());
        btnQuitter.addActionListener(_ -> System.exit(0));

        // Ajout des boutons à la fenêtre
        frame.add(btnAfficher);
        frame.add(btnAjouter);
        frame.add(btnRechercher);
        frame.add(btnSupprimer);
        frame.add(btnjuger);
        frame.add(btnQuitter);

        // Affichage de la fenêtre
        frame.setVisible(true);
    }

    public void afficherEleves() {
        Eleve[] tableauEleves = gestion.getTableauEleves();
        int nombreEleves = gestion.getNombreEleves();

        if (nombreEleves == 0) {
            JOptionPane.showMessageDialog(frame, "❌ Aucun élève dans le tableau.");
        } else {
            String[] listeEleves = new String[nombreEleves];
            for (int i = 0; i < nombreEleves; i++) {
                listeEleves[i] = tableauEleves[i].toString();
            }

            JList<String> jListEleves = new JList<>(listeEleves);
            JOptionPane.showMessageDialog(frame, new JScrollPane(jListEleves), "📜 Liste des élèves", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void ajouterEleve() {
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField classeField = new JTextField();
        JTextField moyenneField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Classe :"));
        panel.add(classeField);
        panel.add(new JLabel("Moyenne :"));
        panel.add(moyenneField);

        int option = JOptionPane.showConfirmDialog(frame, panel, "Ajouter un Élève", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String classe = classeField.getText();
                double moyenne = Double.parseDouble(moyenneField.getText());

                Eleve eleve = new Eleve(nom, prenom, classe, moyenne);
                gestion.ajouterEleve(eleve);

                JOptionPane.showMessageDialog(frame, "✅ Élève ajouté avec succès !");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "❌ Veuillez entrer une moyenne valide !");
            }
        }
    }

    public void rechercherEleve() {
        String nomRecherche = JOptionPane.showInputDialog(frame, "🔍 Entrez le nom de l'élève à rechercher :");

        if (nomRecherche != null && !nomRecherche.isEmpty()) {
            Eleve[] tableauEleves = gestion.getTableauEleves();
            int nombreEleves = gestion.getNombreEleves();

            boolean trouve = false;
            for (int i = 0; i < nombreEleves; i++) {
                if (tableauEleves[i].getNom().equalsIgnoreCase(nomRecherche)) {
                    JOptionPane.showMessageDialog(frame, tableauEleves[i].toString());
                    trouve = true;
                    break;
                }
            }

            if (!trouve) {
                JOptionPane.showMessageDialog(frame, "🔍 Élève non trouvé.");
            }
        }
    }

    public void supprimerEleve() {
        String moyenneStr = JOptionPane.showInputDialog(frame, "🗑 Entrez la moyenne de l'élève à supprimer :");

        if (moyenneStr != null && !moyenneStr.isEmpty()) {
            try {
                double moyenne = Double.parseDouble(moyenneStr);
                gestion.supprimerEleveParMoyenne(moyenne);
                JOptionPane.showMessageDialog(frame, "✅ Élève supprimé avec succès !");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "❌ Veuillez entrer une moyenne valide !");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "❌ Annulé ou champ vide !");
        }
    }

    public void jugerEleves() {
        Eleve[] tableauEleves = gestion.getTableauEleves();
        int nombreEleves = gestion.getNombreEleves();
    
        if (nombreEleves == 0) {
            JOptionPane.showMessageDialog(frame, "❌ Aucun élève dans le tableau.");
        } else {
            StringBuilder resultats = new StringBuilder("Résultats du jugement :\n");
    
            for (int i = 0; i < nombreEleves; i++) {
                Eleve eleve = tableauEleves[i];
                double moyenne = eleve.getMoyenne();
                String jugement;
    
                if (moyenne >= 10) {
                    jugement = "✅ Réussit";
                } else if (moyenne < 7) {
                    jugement = "❌ Échoue";
                } else {
                    jugement = "⚠️ Se Représente";
                }
    
                resultats.append(eleve.getNom())
                         .append(" ")
                         .append(eleve.getPrenom())
                         .append(" - Moyenne: ")
                         .append(moyenne)
                         .append(" → ")
                         .append(jugement)
                         .append("\n");
            }
    
            JOptionPane.showMessageDialog(frame, resultats.toString(), "⚖ Jugement des élèves", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionElevesGUI::new);
    }
}
