
package uiAdmin;

import dao.AdminDAO;
import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AdminLoginFrame extends JFrame {

    public AdminLoginFrame() {
        setTitle("Connexion Administrateur");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Récupérer la taille de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Créer un panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Panel pour le contenu centré
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Panel pour le formulaire avec contrainte de taille maximum
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        
        // Limiter la largeur maximale du formulaire
        Dimension maxFormSize = new Dimension(500, 400);
        formPanel.setMaximumSize(maxFormSize);
        formPanel.setPreferredSize(maxFormSize);
        
        // Création des contraintes pour GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Titre
        JLabel titleLabel = new JLabel("Connexion Administrateur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        formPanel.add(titleLabel, gbc);
        
        // Réinitialiser les contraintes
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(emailLabel, gbc);
        
        JTextField emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(emailField, gbc);
        
        // Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(passwordLabel, gbc);
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(passwordField, gbc);
        
        // Bouton de connexion
        JButton loginBtn = new JButton("Connexion");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setPreferredSize(new Dimension(150, 40));
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginBtn, gbc);
        
        // Ajouter le formulaire au centre
        centerPanel.add(formPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Footer optionnel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel footerLabel = new JLabel("© 2024 - Système d'administration");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        // Action du bouton
        loginBtn.addActionListener(e -> {
            try {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                // Validation simple
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Veuillez remplir tous les champs",
                        "Champs obligatoires",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                
                Admin admin = new AdminDAO().login(email, password);
                
                if (admin != null) {
                    // Afficher un message de succès
                    JOptionPane.showMessageDialog(
                        this,
                        "Connexion réussie !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Ouvrir la fenêtre principale
                    SwingUtilities.invokeLater(() -> {
                        new AdminHomeFrame(admin);
                        dispose();
                    });
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Identifiants incorrects",
                        "Erreur d'authentification",
                        JOptionPane.ERROR_MESSAGE
                    );
                    
                    // Effacer le mot de passe
                    passwordField.setText("");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Erreur de connexion à la base de données: " + ex.getMessage(),
                    "Erreur système",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        });
        
        // Support pour la touche Entrée
        getRootPane().setDefaultButton(loginBtn);
        
        add(mainPanel);
        setVisible(true);
        
        // Mettre le focus sur le champ email
        SwingUtilities.invokeLater(() -> emailField.requestFocusInWindow());
    }
}
//package uiAdmin;
//
//import dao.AdminDAO;
//import model.Admin;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.SQLException;
//
//public class AdminLoginFrame extends JFrame {
//
//    public AdminLoginFrame() {
//
//        setTitle("Connexion Administrateur");
//        setSize(400, 300);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
//        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//
//        JTextField emailField = new JTextField();
//        JPasswordField passwordField = new JPasswordField();
//
//        JButton loginBtn = new JButton("Connexion");
//
//        panel.add(new JLabel("Email"));
//        panel.add(emailField);
//        panel.add(new JLabel("Mot de passe"));
//        panel.add(passwordField);
//        panel.add(new JLabel());
//        panel.add(loginBtn);
//
//        add(panel);
//
//        loginBtn.addActionListener(e -> {
//            try {
//                Admin admin = new AdminDAO().login(
//                        emailField.getText(),
//                        new String(passwordField.getPassword())
//                );
//
//                if (admin != null) {
//                    new AdminHomeFrame(admin);
//                    dispose();
//                } else {
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "Identifiants incorrects",
//                            "Erreur",
//                            JOptionPane.ERROR_MESSAGE
//                    );
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        });
//
//        setVisible(true);
//    }
//}
