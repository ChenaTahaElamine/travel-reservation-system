
package ui;

import dao.ClientDAO;
import model.Client;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {

    private JTextField nomField, prenomField, emailField, telephoneField;
    private JPasswordField passwordField;
    private ClientDAO clientDAO = new ClientDAO();

    // Palette de couleurs (identique à LoginFrame pour la cohérence)
    private final Color COLOR_BG = new Color(18, 18, 18);
    private final Color COLOR_CARD = new Color(30, 30, 30);
    private final Color COLOR_ACCENT = new Color(46, 204, 113); // Vert pour la création
    private final Color COLOR_TEXT = Color.WHITE;

    public RegisterFrame() {
        setTitle("VoyageEZ - Création de compte");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal (Centrage)
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(COLOR_BG);

        // Carte de formulaire
        JPanel card = createStyledCard();

        mainPanel.add(card);
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createStyledCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(50, 50, 50), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));
        card.setPreferredSize(new Dimension(500, 650));
        card.setMaximumSize(new Dimension(500, 650));

        // Titre
        JLabel title = new JLabel("Rejoignez VoyageEZ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(COLOR_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 25, 0));
        card.add(title);

        // Initialisation et ajout des champs
        nomField = createField(card, "NOM");
        prenomField = createField(card, "PRÉNOM");
        telephoneField = createField(card, "TÉLÉPHONE");
        emailField = createField(card, "EMAIL");
        passwordField = createPasswordField(card, "MOT DE PASSE");

        // Boutons
        JButton createBtn = createButton("Créer mon compte", COLOR_ACCENT, Color.WHITE);
        JButton backBtn = createButton("Retour à la connexion", COLOR_CARD, new Color(200, 200, 200));
        backBtn.setBorder(new LineBorder(new Color(70, 70, 70), 1));

        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(createBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(backBtn);

        // --- Actions ---
        createBtn.addActionListener(e -> handleRegistration());
        backBtn.addActionListener(e -> {
            new LoginFrame();
            this.dispose();
        });

        return card;
    }

    private JTextField createField(JPanel panel, String labelText) {
        addLabel(panel, labelText);
        JTextField field = new JTextField();
        styleComponent(field);
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return field;
    }

    private JPasswordField createPasswordField(JPanel panel, String labelText) {
        addLabel(panel, labelText);
        JPasswordField field = new JPasswordField();
        styleComponent(field);
        panel.add(field);
        return field;
    }

    private void addLabel(JPanel panel, String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(130, 130, 130));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(5, 0, 3, 0));
        panel.add(lbl);
    }

    private void styleComponent(JComponent comp) {
        comp.setMaximumSize(new Dimension(420, 35));
        comp.setBackground(new Color(45, 45, 45));
        comp.setForeground(Color.WHITE);
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(70, 70, 70), 1),
            new EmptyBorder(0, 10, 0, 10)
        ));
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(420, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void handleRegistration() {
        if (nomField.getText().isEmpty() || emailField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client client = new Client(0, nomField.getText(), prenomField.getText(), telephoneField.getText(), emailField.getText(), new String(passwordField.getPassword()));

        try {
            clientDAO.insert(client);
            JOptionPane.showMessageDialog(this, "Bienvenue ! Votre compte est prêt.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame();
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
//package ui;
//
//import dao.ClientDAO;
//import model.Client;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.sql.SQLException;
//
//public class RegisterFrame extends JFrame {
//
//    private JTextField nomField;
//    private JTextField prenomField;
//    private JTextField emailField;
//    private JTextField telephoneField;
//    private JPasswordField passwordField;
//
//    private ClientDAO clientDAO = new ClientDAO();
//
//    public RegisterFrame() {
//
//        setTitle("Création de compte");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // ===== Background =====
//        JPanel background = new JPanel(new GridBagLayout());
//        background.setBackground(Color.BLACK);
//        add(background);
//
//        // ===== Carte centrale =====
//        JPanel formPanel = new JPanel(new GridBagLayout());
//        formPanel.setPreferredSize(new Dimension(480, 460));
//        formPanel.setBackground(Color.LIGHT_GRAY);
//        formPanel.setBorder(new EmptyBorder(25, 30, 25, 30));
//        background.add(formPanel);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // ===== Titre =====
//        JLabel title = new JLabel("Créer un compte");
//        title.setFont(new Font("Arial", Font.BOLD, 24));
//        title.setHorizontalAlignment(SwingConstants.CENTER);
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        formPanel.add(title, gbc);
//
//        gbc.gridwidth = 1;
//
//        // ===== Champs =====
//        gbc.gridy++;
//        formPanel.add(new JLabel("Nom :"), gbc);
//        gbc.gridx = 1;
//        nomField = new JTextField();
//        formPanel.add(nomField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy++;
//        formPanel.add(new JLabel("Prénom :"), gbc);
//        gbc.gridx = 1;
//        prenomField = new JTextField();
//        formPanel.add(prenomField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy++;
//        formPanel.add(new JLabel("Téléphone :"), gbc);
//        gbc.gridx = 1;
//        telephoneField = new JTextField();
//        formPanel.add(telephoneField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy++;
//        formPanel.add(new JLabel("Email :"), gbc);
//        gbc.gridx = 1;
//        emailField = new JTextField();
//        formPanel.add(emailField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy++;
//        formPanel.add(new JLabel("Mot de passe :"), gbc);
//        gbc.gridx = 1;
//        passwordField = new JPasswordField();
//        formPanel.add(passwordField, gbc);
//
//        // ===== Boutons =====
//        gbc.gridx = 0;
//        gbc.gridy++;
//        gbc.gridwidth = 2;
//
//        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
//        buttonPanel.setBackground(Color.LIGHT_GRAY);
//
//        JButton createButton = new JButton("Créer le compte");
//        JButton retour = new JButton("Retour");
//
//        styleButton(createButton, new Color(144, 238, 144));
//        styleButton(retour, new Color(211, 211, 211));
//
//        buttonPanel.add(createButton);
//        buttonPanel.add(retour);
//
//        formPanel.add(buttonPanel, gbc);
//
//        // ===== Actions (inchangées) =====
//        createButton.addActionListener(e -> {
//            if (nomField.getText().isEmpty()
//                    || prenomField.getText().isEmpty()
//                    || emailField.getText().isEmpty()
//                    || passwordField.getPassword().length == 0) {
//
//                JOptionPane.showMessageDialog(this,
//                        "Veuillez remplir tous les champs obligatoires",
//                        "Erreur",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            Client client = new Client(
//                    0,
//                    nomField.getText(),
//                    prenomField.getText(),
//                    telephoneField.getText(),
//                    emailField.getText(),
//                    new String(passwordField.getPassword())
//            );
//
//            try {
//                clientDAO.insert(client);
//
//                JOptionPane.showMessageDialog(this,
//                        "Compte créé avec succès",
//                        "Succès",
//                        JOptionPane.INFORMATION_MESSAGE);
//
//                this.dispose();
//                new LoginFrame();
//
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(this,
//                        "Erreur lors de la création du compte",
//                        "Erreur",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        retour.addActionListener(e -> {
//            new LoginFrame();
//            this.dispose();
//        });
//
//        setVisible(true);
//    }
//
//    // ===== UI uniquement =====
//    private void styleButton(JButton btn, Color bg) {
//        btn.setBackground(bg);
//        btn.setFocusPainted(false);
//        btn.setFont(new Font("Arial", Font.BOLD, 14));
//        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//    }
//}
//
// 
