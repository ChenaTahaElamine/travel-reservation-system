//package ui;
//
//import model.Client;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.sql.SQLException;
//
//public class HomeFrame extends JFrame {
//
//    public HomeFrame(Client client) {
//
//        setTitle("Accueil");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        // ===== Background principal =====
//        JPanel background = new JPanel(new GridBagLayout());
//        background.setBackground(Color.GRAY);
//        add(background);
//
//        // ===== Carte centrale =====
//        JPanel card = new JPanel();
//        card.setLayout(new BorderLayout(20, 20));
//        card.setPreferredSize(new Dimension(520, 320));
//        card.setBackground(new Color(173, 216, 230));
//        card.setBorder(new EmptyBorder(25, 25, 25, 25));
//
//        background.add(card);
//
//        // ===== Titre =====
//        JLabel title = new JLabel("Bienvenue", SwingConstants.CENTER);
//        title.setFont(new Font("Arial", Font.BOLD, 26));
//
//        JLabel subtitle = new JLabel(
//                "Que souhaitez-vous faire aujourd'hui ?",
//                SwingConstants.CENTER
//        );
//        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
//
//        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
//        titlePanel.setOpaque(false);
//        titlePanel.add(title);
//        titlePanel.add(subtitle);
//
//        card.add(titlePanel, BorderLayout.NORTH);
//
//        // ===== Boutons centraux =====
//        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
//        buttonPanel.setOpaque(false);
//
//        JButton reservationBtn = new JButton("Réserver un voyage");
//        JButton accountBtn = new JButton("Paramètres du compte");
//        JButton backBtn = new JButton("Déconnexion");
//
//        styleButton(reservationBtn, new Color(144, 238, 144));
//        styleButton(accountBtn, new Color(255, 255, 153));
//        styleButton(backBtn, new Color(240, 128, 128));
//
//        buttonPanel.add(reservationBtn);
//        buttonPanel.add(accountBtn);
//        buttonPanel.add(backBtn);
//
//        card.add(buttonPanel, BorderLayout.CENTER);
//
//        // ===== Actions (inchangées) =====
//        reservationBtn.addActionListener(e -> {
//            try {
//                new ReservationFrame(client);
//                dispose();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        });
//
//        accountBtn.addActionListener(e -> {
//            new AccountSettingsFrame(client);
//            dispose();
//        });
//
//        backBtn.addActionListener(e -> {
//            new LoginFrame();
//            dispose();
//        });
//
//        setVisible(true);
//    }
//
//    // ===== Style UI uniquement =====
//    private void styleButton(JButton btn, Color bg) {
//        btn.setBackground(bg);
//        btn.setFocusPainted(false);
//        btn.setFont(new Font("Arial", Font.BOLD, 15));
//        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
//    }
//}


package ui;

import model.Client;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.SQLException;

public class HomeFrame extends JFrame {

    // Couleurs cohérentes avec Login et Register
    private final Color COLOR_BG = new Color(18, 18, 18);
    private final Color COLOR_CARD = new Color(30, 30, 30);
    private final Color COLOR_TEXT = Color.WHITE;
    private final Color COLOR_ACCENT = new Color(0, 150, 255); // Bleu

    public HomeFrame(Client client) {
        setTitle("VoyageEZ - Tableau de bord");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel Principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(COLOR_BG);

        // Carte centrale
        JPanel card = createHomeCard(client);

        mainPanel.add(card);
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHomeCard(Client client) {
        JPanel card = new JPanel(new BorderLayout(0, 30));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(50, 50, 50), 1),
            new EmptyBorder(40, 50, 40, 50)
        ));
        card.setPreferredSize(new Dimension(600, 450));

        // --- En-tête (Bienvenue) ---
        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Bonjour, " + client.getPrenom() + " ! 👋");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(COLOR_TEXT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitle = new JLabel("Où partons-nous aujourd'hui ?");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(150, 150, 150));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(welcomeLabel);
        header.add(subtitle);

        // --- Corps (Boutons d'action) ---
        JPanel body = new JPanel(new GridLayout(3, 1, 0, 15));
        body.setOpaque(false);

        JButton reservationBtn = createMenuButton("✈️  Réserver un nouveau voyage", COLOR_ACCENT);
        JButton accountBtn = createMenuButton("⚙️  Paramètres de mon compte", new Color(60, 60, 60));
        JButton logoutBtn = createMenuButton("🚪  Déconnexion", new Color(200, 50, 50));

        body.add(reservationBtn);
        body.add(accountBtn);
        body.add(logoutBtn);

        // --- Assemblage ---
        card.add(header, BorderLayout.NORTH);
        card.add(body, BorderLayout.CENTER);

        // --- Actions ---
        reservationBtn.addActionListener(e -> {
            try {
                new ReservationFrame(client);
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erreur de chargement des voyages.");
            }
        });

        accountBtn.addActionListener(e -> {
            new AccountSettingsFrame(client);
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Voulez-vous vraiment vous déconnecter ?", "Déconnexion", 
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                new LoginFrame();
                dispose();
            }
        });

        return card;
    }

    private JButton createMenuButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet au survol
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });

        return btn;
    }
}
