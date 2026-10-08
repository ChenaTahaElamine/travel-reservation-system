package uiAdmin;

import dao.ChauffeurDAO;
import model.Chauffeur;

import javax.swing.*;
import java.awt.*;

public class DriverDialog extends JDialog {

    public DriverDialog(JFrame parent) {
        super(parent, "Ajouter Conducteur", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);

        // Définition des champs
        JTextField nom = new JTextField();
        JTextField prenom = new JTextField();
        JTextField email = new JTextField(); // Déplacé pour l'ordre logique
        JTextField tel = new JTextField();

        JButton save = new JButton("Enregistrer");
        
        save.addActionListener(e -> {
            // Vérification si un champ est vide (trim retire les espaces inutiles)
            if (nom.getText().trim().isEmpty() || 
                prenom.getText().trim().isEmpty() || 
                email.getText().trim().isEmpty() || 
                tel.getText().trim().isEmpty()) {
                
                // Affiche un message d'erreur si un champ manque
                JOptionPane.showMessageDialog(this, 
                    "Veuillez remplir tous les champs obligatoires.", 
                    "Champs manquants", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    // Si tout est rempli, on procède à l'enregistrement
                    Chauffeur c = new Chauffeur(0, nom.getText(), prenom.getText(), email.getText(), tel.getText());
                    new ChauffeurDAO().insert(c);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement : " + ex.getMessage());
                }
            }
        });

         
//        save.addActionListener(e -> {
//            try {
//                // L'ordre des arguments dans le constructeur reste identique au modèle Chauffeur
//                Chauffeur c = new Chauffeur(0, nom.getText(), prenom.getText(), email.getText(), tel.getText());
//                new ChauffeurDAO().insert(c);
//                dispose();
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement");
//            }
//        });

        // Modification de la grille : 5 lignes au lieu de 4 pour inclure tous les champs proprement
        setLayout(new GridLayout(5, 2, 10, 10));

        // 1. Ligne Nom
        add(new JLabel(" Nom :"));
        add(nom);

        // 2. Ligne Prénom
        add(new JLabel(" Prénom :"));
        add(prenom);

        // 3. Ligne Email
        add(new JLabel(" Email :"));
        add(email);

        // 4. Ligne Téléphone
        add(new JLabel(" Téléphone :"));
        add(tel);

        // 5. Ligne Bouton
        add(new JLabel()); // Case vide pour aligner le bouton à droite
        add(save);

        setVisible(true);
    }
}


//package uiAdmin;
//
//import dao.ChauffeurDAO;
//import model.Chauffeur;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class DriverDialog extends JDialog {
//
//    public DriverDialog(JFrame parent) {
//        super(parent, "Ajouter Conducteur", true);
//        setSize(350, 250);
//        setLocationRelativeTo(parent);
//
//        JTextField nom = new JTextField();
//        JTextField prenom = new JTextField();
//        JTextField tel = new JTextField();
//        JTextField email = new JTextField();
//
//        JButton save = new JButton("Enregistrer");
//         
//        save.addActionListener(e -> {
//            try {
//            	
//            	Chauffeur c = new Chauffeur(0, nom.getText(), prenom.getText(), email.getText(), tel.getText());
//            	new ChauffeurDAO().insert(c);
//                dispose();
//                
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "Erreur");
//            }
//        });
//
//        setLayout(new GridLayout(4, 2, 10, 10));
//        add(new JLabel("Nom"));
//        add(nom);
//        add(new JLabel("Prénom"));
//        add(prenom);
//        add(new JLabel("Téléphone"));
//        
//        add(email);
//        add(new JLabel("Email"));
//        
//        add(tel);
//        add(new JLabel());
//        add(save);
//
//        setVisible(true);
//    }
//}
