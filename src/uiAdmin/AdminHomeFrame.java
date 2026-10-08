package uiAdmin;

import model.Admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AdminHomeFrame extends JFrame {

    public AdminHomeFrame(Admin admin) {
        setTitle("Accueil Administrateur");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Panel principal avec fond dégradé
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(30, 30, 50); // Bleu foncé
                Color color2 = new Color(10, 10, 20);  // Presque noir
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);
        
        // En-tête avec informations de l'admin
        JPanel headerPanel = createHeaderPanel(admin);
        
        // Zone centrale avec les boutons
        JPanel centerPanel = createCenterPanel(admin);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        
        // Assemblage de l'interface
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        // Ajout d'un menu bar optionnel
        setJMenuBar(createMenuBar(admin));
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel(Admin admin) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 30, 50, 200)); // Transparent
        header.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Titre principal
        JLabel title = new JLabel("Espace Administration", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        
        // Information de l'administrateur
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setOpaque(false);
        
        JLabel welcomeLabel = new JLabel("Connecté en tant que : " + admin.getEmail());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(200, 200, 200));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        infoPanel.add(welcomeLabel);
        
        header.add(title, BorderLayout.CENTER);
        header.add(infoPanel, BorderLayout.SOUTH);
        
        return header;
    }
    
    private JPanel createCenterPanel(Admin admin) {
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        
        // Conteneur pour les boutons avec contrainte de taille
        JPanel buttonContainer = new JPanel(new GridLayout(1, 2, 30, 0));
        buttonContainer.setOpaque(false);
        buttonContainer.setPreferredSize(new Dimension(800, 200));
        buttonContainer.setMaximumSize(new Dimension(800, 200));
        
        JButton manageBtn = createStyledButton("Gestion Globale", 
            new Color(52, 152, 219), // Bleu
            new Color(41, 128, 185)  // Bleu foncé au survol
        );
        
        JButton logoutBtn = createStyledButton("Déconnexion",
            new Color(231, 76, 60),  // Rouge
            new Color(192, 57, 43)   // Rouge foncé au survol
        );
        
        // Actions des boutons (identiques à l'original)
        manageBtn.addActionListener(e -> {
            new AdminManagementFrame(admin);
            dispose();
        });
        
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir vous déconnecter ?",
                "Confirmation de déconnexion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                new AdminLoginFrame();
                dispose();
            }
        });
        
        // Ajout d'icônes aux boutons (optionnel)
        try {
            // Utilisation d'icônes standard Swing
            manageBtn.setIcon(new ImageIcon(
                new ImageIcon(getClass().getResource("/icons/manage.png"))
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)
            ));
            
            logoutBtn.setIcon(new ImageIcon(
                new ImageIcon(getClass().getResource("/icons/logout.png"))
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)
            ));
        } catch (Exception e) {
            // Si les icônes ne sont pas disponibles, utiliser du texte uniquement
            System.out.println("Icônes non trouvées, utilisation du texte uniquement");
        }
        
        buttonContainer.add(manageBtn);
        buttonContainer.add(logoutBtn);
        
        // Utilisation de GridBagLayout pour centrer parfaitement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        center.add(buttonContainer, gbc);
        
        return center;
    }
    
    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(30, 30, 50, 200));
        footer.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel statusLabel = new JLabel("Système d'administration - Tous droits réservés");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(180, 180, 180));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        footer.add(statusLabel, BorderLayout.CENTER);
        
        return footer;
    }
    
    private JButton createStyledButton(String text, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(normalColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        // Stylisation du bouton
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(350, 80));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Alignement du texte et de l'icône
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setIconTextGap(15);
        
        return button;
    }
    
    private JMenuBar createMenuBar(Admin admin) {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.setForeground(Color.WHITE);
        
        JMenuItem manageItem = new JMenuItem("Gestion");
        JMenuItem logoutItem = new JMenuItem("Déconnexion");
        
        // Ajout des actions identiques aux boutons
        manageItem.addActionListener(e -> {
            new AdminManagementFrame(admin);
            dispose();
        });
        
        logoutItem.addActionListener(e -> {
            new AdminLoginFrame();
            dispose();
        });
        
        fileMenu.add(manageItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(logoutItem);
        
        // Menu Aide
        JMenu helpMenu = new JMenu("Aide");
        helpMenu.setForeground(Color.WHITE);
        
        JMenuItem aboutItem = new JMenuItem("À propos");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Système d'Administration v1.0\n" +
                "Connecté en tant que: " + admin.getEmail(),
                "À propos",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        // Style de la barre de menu
        menuBar.setBackground(new Color(30, 30, 50));
        menuBar.setBorder(new LineBorder(new Color(60, 60, 80)));
        
        return menuBar;
    }
}

//package uiAdmin;
//
//import model.Admin;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//
//public class AdminHomeFrame extends JFrame {
//
//    public AdminHomeFrame(Admin admin) {
//
//        setTitle("Accueil Administrateur");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        JPanel bg = new JPanel(new BorderLayout());
//        bg.setBackground(Color.DARK_GRAY);
//
//        JLabel title = new JLabel("Espace Administration", JLabel.CENTER);
//        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
//        title.setForeground(Color.WHITE);
//        title.setBorder(new EmptyBorder(30, 10, 30, 10));
//
//        JPanel center = new JPanel(new GridLayout(2, 2, 25, 25));
//        center.setBorder(new EmptyBorder(50, 150, 50, 150));
//        center.setBackground(Color.DARK_GRAY);
//
//        JButton manageBtn = createButton("Gestion Globale");
////        JButton statsBtn = createButton("Statistiques");
////        JButton accountBtn = createButton("Mon Compte");
//        JButton logoutBtn = createButton("Déconnexion");
//
//        center.add(manageBtn);
////        center.add(statsBtn);
////        center.add(accountBtn);
//        center.add(logoutBtn);
//
//        manageBtn.addActionListener(e -> {
//            new AdminManagementFrame(admin);
//            dispose();
//        });
//
//        logoutBtn.addActionListener(e -> {
//        	new AdminLoginFrame();
//            this.dispose();
//        });
//
//        bg.add(title, BorderLayout.NORTH);
//        bg.add(center, BorderLayout.CENTER);
//
//        add(bg);
//        setVisible(true);
//    }
//
//    private JButton createButton(String text) {
//        JButton btn = new JButton(text);
//        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        btn.setFocusPainted(false);
//        return btn;
//    }
//}
