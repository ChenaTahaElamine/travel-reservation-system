package ui;

import dao.ClientDAO;
import model.Client;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private ClientDAO clientDAO = new ClientDAO();

    // Couleurs de thème
    private final Color COLOR_BG = new Color(18, 18, 18);      // Noir profond
    private final Color COLOR_CARD = new Color(30, 30, 30);    // Gris anthracite
    private final Color COLOR_ACCENT = new Color(0, 150, 255); // Bleu moderne
    private final Color COLOR_TEXT = Color.WHITE;

    public LoginFrame() {
        setTitle("VoyageEZ - Authentification");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel principal avec GridBagLayout pour le centrage absolu
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(COLOR_BG);

        // --- Création de la Carte de Connexion ---
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
            new EmptyBorder(40, 50, 40, 50)
        ));
        card.setPreferredSize(new Dimension(450, 550));
        card.setMaximumSize(new Dimension(450, 550));

        // 1. Icône ou Logo
        JLabel logo = new JLabel("🌍");
        logo.setFont(new Font("Segoe UI", Font.PLAIN, 60));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 2. Titre
        JLabel title = new JLabel("Bienvenue");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(COLOR_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(10, 0, 30, 0));

        // 3. Champs de saisie
        emailField = createStyledTextField("Email");
        passwordField = createStyledPasswordField();

        // 4. Boutons
        JButton loginBtn = createPrimaryButton("Se connecter");
        JButton registerBtn = createSecondaryButton("Créer un compte");

        // --- Assemblage ---
        card.add(logo);
        card.add(title);
        
        addLabel(card, "ADRESSE EMAIL");
        card.add(emailField);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        
        addLabel(card, "MOT DE PASSE");
        card.add(passwordField);
        card.add(Box.createRigidArea(new Dimension(0, 40)));
        
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(registerBtn);

        // Actions
        loginBtn.addActionListener(e -> authenticate());
        registerBtn.addActionListener(e -> {
            new RegisterFrame();
            this.dispose();
        });

        return card;
    }

    private void addLabel(JPanel panel, String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(new Color(150, 150, 150));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        panel.add(lbl);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        styleField(field);
        return field;
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(350, 40));
        field.setBackground(new Color(45, 45, 45));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(70, 70, 70), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(350, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(COLOR_ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(350, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(COLOR_CARD);
        btn.setForeground(COLOR_ACCENT);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(COLOR_ACCENT, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void authenticate() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Champs vides !", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Client client = clientDAO.authenticate(email, password).getClient();

        if (client == null) {
            JOptionPane.showMessageDialog(this, "Identifiants incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            new HomeFrame(client);
            this.dispose();
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
//
//public class LoginFrame extends JFrame {
//
//    private JTextField emailField;
//    private JPasswordField passwordField;
//    private ClientDAO clientDAO = new ClientDAO();
//
//    public LoginFrame() {
//        setTitle("Authentification");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        // ===== Background =====
//        JPanel background = new JPanel(new GridBagLayout());
//        background.setBackground(Color.BLACK);
//
//        // ===== Container central =====
//        JPanel container = new JPanel(new BorderLayout(0, 20));
//        container.setBackground(Color.BLACK);
//
//        // ===== Titre =====
//        JLabel title = new JLabel("Bienvenue sur l'application de réservation de voyages");
//        title.setForeground(Color.WHITE);
//        title.setFont(new Font("Arial", Font.BOLD, 26));
//        title.setHorizontalAlignment(SwingConstants.CENTER);
//
//        // ===== Formulaire =====
//        JPanel form = new JPanel(new GridLayout(3, 2, 15, 15));
//        form.setBackground(Color.LIGHT_GRAY);
//        form.setBorder(new EmptyBorder(25, 30, 25, 30));
//
//        emailField = new JTextField();
//        passwordField = new JPasswordField();
//
//        JButton loginBtn = new JButton("Vérifier");
//        JButton registerBtn = new JButton("Créer un compte");
//
//        styleButton(loginBtn, new Color(173, 216, 230));
//        styleButton(registerBtn, new Color(200, 200, 200));
//
//        form.add(new JLabel("Email :"));
//        form.add(emailField);
//        form.add(new JLabel("Mot de passe :"));
//        form.add(passwordField);
//        form.add(loginBtn);
//        form.add(registerBtn);
//
//        container.add(title, BorderLayout.NORTH);
//        container.add(form, BorderLayout.CENTER);
//
//        background.add(container);
//        add(background);
//
//        // ===== Actions =====
//        loginBtn.addActionListener(e -> authenticate());
//
//        registerBtn.addActionListener(e -> {
//        	new RegisterFrame();
//            this.dispose();
//            
//        });
//
//        setVisible(true);
//    }
//
//    private void authenticate() {
//        Client client = clientDAO.authenticate(
//                emailField.getText(),
//                new String(passwordField.getPassword())
//        ).getClient();
//
//        if (client == null) {
//            JOptionPane.showMessageDialog(this,
//                    "Email ou mot de passe incorrect",
//                    "Erreur",
//                    JOptionPane.ERROR_MESSAGE);
//        } else {
//        	
//            new HomeFrame(client);
//            this.dispose();
//        }
//    }
//
//    // ===== Méthode UI uniquement (aucune logique) =====
//    private void styleButton(JButton btn, Color bg) {
//        btn.setBackground(bg);
//        btn.setFocusPainted(false);
//        btn.setFont(new Font("Arial", Font.BOLD, 14));
//        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//    }
//}
//
// 
