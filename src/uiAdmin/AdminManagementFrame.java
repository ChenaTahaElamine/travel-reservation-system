
package uiAdmin;

import dao.BusDAO;
import dao.ChauffeurDAO;
import dao.VoyageDAO;
import model.Admin;
import model.Bus;
import model.Chauffeur;
import model.Voyage;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminManagementFrame extends JFrame {

    private Admin admin;
    private JTabbedPane tabs; // Déclaré comme variable d'instance

    private DefaultTableModel busModel;
    private DefaultTableModel voyageModel;
    private DefaultTableModel driverModel;

    private JTable busTable;
    private JTable voyageTable;
    private JTable driverTable;

    public AdminManagementFrame(Admin admin) {
        this.admin = admin;

        setTitle("Gestion Administrateur - " + admin.getEmail());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel principal avec fond dégradé
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(240, 245, 250);
                Color color2 = new Color(220, 230, 240);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);

        // Header avec navigation
        JPanel headerPanel = createHeaderPanel();

        // Onglets de gestion
        tabs = createStyledTabbedPane(); // Initialisé ici
        tabs.addTab("Bus", createBusPanel());
        tabs.addTab("Voyages", createVoyagePanel());
        tabs.addTab("Conducteurs", createDriverPanel());

        // Footer avec bouton retour
        JPanel footerPanel = createFooterPanel();

        // Assemblage
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Ajout de la barre de menu
        setJMenuBar(createMenuBar());

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 100));
        header.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        header.setPreferredSize(new Dimension(getWidth(), 80));

        // Titre principal
        JLabel title = new JLabel("Gestion Globale", JLabel.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        // Sous-titre avec informations
        JLabel subtitle = new JLabel("Administrateur: " + admin.getEmail(), JLabel.RIGHT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(200, 220, 255));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(subtitle, BorderLayout.NORTH);

        header.add(title, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JTabbedPane createStyledTabbedPane() {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };

        tabs.setOpaque(false);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabs.setBackground(new Color(255, 255, 255, 200));
        tabs.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Style des onglets
        tabs.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                         int x, int y, int w, int h, boolean isSelected) {
                // Pas de bordure par défaut
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement,
                                            int tabIndex, int x, int y,
                                            int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected) {
                    g2d.setColor(new Color(30, 60, 100));
                } else {
                    g2d.setColor(new Color(100, 130, 170));
                }

                g2d.fillRoundRect(x + 2, y + 2, w - 4, h - 2, 8, 8);
            }

            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 30;
            }

            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 10;
            }
        });

        return tabs;
    }

    // ================= BUS =================
    private JPanel createBusPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        busModel = new DefaultTableModel(new String[]{"ID", "Matricule", "Capacité", "Statut"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche l'édition directe des cellules
            }
        };
        
        busTable = createStyledTable(busModel);
        busTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
        busTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Matricule
        busTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Capacité
        busTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Statut

        loadBusData();

        // Panel des boutons
        JPanel buttonPanel = createButtonPanel("Bus");
        JButton add = (JButton) buttonPanel.getComponent(0);
        JButton delete = (JButton) buttonPanel.getComponent(1);
        JButton refresh = (JButton) buttonPanel.getComponent(2);

        add.addActionListener(e -> new BusDialog(this));
        delete.addActionListener(e -> deleteBus());
        refresh.addActionListener(e -> loadBusData());

        // Panel de statistiques
        JPanel statsPanel = createStatsPanel("Bus", busModel.getRowCount());

        // Panel supérieur combiné
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(statsPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(createScrollPane(busTable), BorderLayout.CENTER);

        return panel;
    }

    private void loadBusData() {
        busModel.setRowCount(0);
        try {
            List<Bus> buses = new BusDAO().findAll();
            for (Bus b : buses) {
                busModel.addRow(new Object[]{
                        b.getIdBus(),
                        b.getMatricule(),
                        b.getCapacite(),
                        "Disponible" // Vous pouvez ajouter une logique pour déterminer le statut
                });
            }
            updateTabTitle(0, "Bus (" + buses.size() + ")");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors du chargement des bus: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBus() {
        int row = busTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un bus à supprimer.",
                "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Êtes-vous sûr de vouloir supprimer ce bus ?\n" +
                "Matricule: " + busModel.getValueAt(row, 1),
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) busModel.getValueAt(row, 0);
                new BusDAO().delete(id);
                loadBusData();
                JOptionPane.showMessageDialog(this, 
                    "Bus supprimé avec succès.",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la suppression: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ================= VOYAGES =================
    private JPanel createVoyagePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        voyageModel = new DefaultTableModel(
                new String[]{"ID", "Date", "Heure Départ", "Prix", "Places Restantes"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        voyageTable = createStyledTable(voyageModel);
        voyageTable.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
        voyageTable.getColumnModel().getColumn(1).setPreferredWidth(100);  // Date
        voyageTable.getColumnModel().getColumn(2).setPreferredWidth(100);  // Heure
        voyageTable.getColumnModel().getColumn(3).setPreferredWidth(80);   // Prix
        voyageTable.getColumnModel().getColumn(4).setPreferredWidth(120);  // Places

        loadVoyageData();

        JPanel buttonPanel = createButtonPanel("Voyage");
        JButton add = (JButton) buttonPanel.getComponent(0);
        JButton delete = (JButton) buttonPanel.getComponent(1);
        JButton refresh = (JButton) buttonPanel.getComponent(2);

        add.addActionListener(e -> new VoyageDialog(this));
        delete.addActionListener(e -> deleteVoyage());
        refresh.addActionListener(e -> loadVoyageData());

        JPanel statsPanel = createStatsPanel("Voyages", voyageModel.getRowCount());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(statsPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(createScrollPane(voyageTable), BorderLayout.CENTER);

        return panel;
    }

    private void loadVoyageData() {
        voyageModel.setRowCount(0);
        try {
            List<Voyage> voyages = new VoyageDAO().findAll();
            for (Voyage v : voyages) {
                voyageModel.addRow(new Object[]{
                        v.getIdVoyage(),
                        v.getDateVoyage(),
                        v.getHeureDepart(),
                        String.format("%.2f €", v.getPrix()),
                        "20/40" // À remplacer par la logique réelle
                });
            }
            updateTabTitle(1, "Voyages (" + voyages.size() + ")");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors du chargement des voyages: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteVoyage() {
        int row = voyageTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un voyage à supprimer.",
                "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Êtes-vous sûr de vouloir supprimer ce voyage ?\n" +
                "Date: " + voyageModel.getValueAt(row, 1) + " " + voyageModel.getValueAt(row, 2),
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) voyageModel.getValueAt(row, 0);
                new VoyageDAO().delete(id);
                loadVoyageData();
                JOptionPane.showMessageDialog(this, 
                    "Voyage supprimé avec succès.",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la suppression: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ================= CONDUCTEURS =================
    private JPanel createDriverPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        driverModel = new DefaultTableModel(
                new String[]{"ID", "Nom", "Prénom", "Email", "Téléphone", "Disponible"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        driverTable = createStyledTable(driverModel);
        driverTable.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
        driverTable.getColumnModel().getColumn(1).setPreferredWidth(100);  // Nom
        driverTable.getColumnModel().getColumn(2).setPreferredWidth(100);  // Prénom
        driverTable.getColumnModel().getColumn(3).setPreferredWidth(180);  // Email
        driverTable.getColumnModel().getColumn(4).setPreferredWidth(120);  // Téléphone
        driverTable.getColumnModel().getColumn(5).setPreferredWidth(100);  // Disponible

        loadDriverData();

        JPanel buttonPanel = createButtonPanel("Conducteur");
        JButton add = (JButton) buttonPanel.getComponent(0);
        JButton delete = (JButton) buttonPanel.getComponent(1);
        JButton refresh = (JButton) buttonPanel.getComponent(2);

        add.addActionListener(e -> new DriverDialog(this));
        delete.addActionListener(e -> deleteDriver());
        refresh.addActionListener(e -> loadDriverData());

        JPanel statsPanel = createStatsPanel("Conducteurs", driverModel.getRowCount());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(statsPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(createScrollPane(driverTable), BorderLayout.CENTER);

        return panel;
    }

    private void loadDriverData() {
        driverModel.setRowCount(0);
        try {
            List<Chauffeur> chauffeurs = new ChauffeurDAO().findAll();
            for (Chauffeur c : chauffeurs) {
                driverModel.addRow(new Object[]{
                        c.getIdChauffeur(),
                        c.getNom(),
                        c.getPrenom(),
                        c.getEmail(),
                        c.getTelephone(),
                        "Oui" // À remplacer par la logique réelle
                });
            }
            updateTabTitle(2, "Conducteurs (" + chauffeurs.size() + ")");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors du chargement des conducteurs: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDriver() {
        int row = driverTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un conducteur à supprimer.",
                "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Êtes-vous sûr de vouloir supprimer ce conducteur ?\n" +
                "Nom: " + driverModel.getValueAt(row, 1) + " " + driverModel.getValueAt(row, 2),
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) driverModel.getValueAt(row, 0);
                new ChauffeurDAO().delete(id);
                loadDriverData();
                JOptionPane.showMessageDialog(this, 
                    "Conducteur supprimé avec succès.",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la suppression: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ================= MÉTHODES UTILITAIRES =================
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        
        // Style du tableau
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(30, 60, 100, 100));
        table.setSelectionForeground(Color.BLACK);
        
        // Style de l'en-tête
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(30, 60, 100));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Tri des colonnes
        table.setAutoCreateRowSorter(true);
        
        return table;
    }

    private JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    private JPanel createButtonPanel(String entityName) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setOpaque(false);
        
        String[] buttonTexts = {"Ajouter " + entityName, "Supprimer", "Actualiser"};
        Color[] colors = {
            new Color(46, 204, 113), // Vert pour Ajouter
            new Color(231, 76, 60),  // Rouge pour Supprimer
            new Color(52, 152, 219)  // Bleu pour Actualiser
        };
        
        for (int i = 0; i < 3; i++) {
            JButton button = new JButton(buttonTexts[i]);
            styleButton(button, colors[i]);
            panel.add(button);
        }
        
        return panel;
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    private JPanel createStatsPanel(String entityName, int count) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setOpaque(false);
        
        JLabel countLabel = new JLabel(count + " " + entityName);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        countLabel.setForeground(new Color(30, 60, 100));
        
        JLabel iconLabel = new JLabel("📊");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        
        panel.add(iconLabel);
        panel.add(countLabel);
        
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(30, 60, 100));
        footer.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JButton backBtn = new JButton("← Retour à l'accueil");
        styleButton(backBtn, new Color(149, 165, 166));
        backBtn.addActionListener(e -> {
            new AdminHomeFrame(admin);
            dispose();
        });
        
        JLabel timestamp = new JLabel(new java.util.Date().toString());
        timestamp.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        timestamp.setForeground(new Color(200, 220, 255));
        
        footer.add(backBtn, BorderLayout.WEST);
        footer.add(timestamp, BorderLayout.EAST);
        
        return footer;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 60, 100));
        
        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");
        styleMenu(fileMenu);
        
        JMenuItem refreshItem = new JMenuItem("Actualiser tout");
        JMenuItem backItem = new JMenuItem("Retour à l'accueil");
        JMenuItem exitItem = new JMenuItem("Quitter");
        
        refreshItem.addActionListener(e -> {
            loadBusData();
            loadVoyageData();
            loadDriverData();
        });
        
        backItem.addActionListener(e -> {
            new AdminHomeFrame(admin);
            dispose();
        });
        
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(refreshItem);
        fileMenu.addSeparator();
        fileMenu.add(backItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Menu Aide
        JMenu helpMenu = new JMenu("Aide");
        styleMenu(helpMenu);
        
        JMenuItem aboutItem = new JMenuItem("À propos");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Système de Gestion de Transport\n" +
                "Version 1.0\n" +
                "Administrateur: " + admin.getEmail(),
                "À propos", JOptionPane.INFORMATION_MESSAGE);
        });
        
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    private void styleMenu(JMenu menu) {
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private void updateTabTitle(int tabIndex, String title) {
        SwingUtilities.invokeLater(() -> {
            if (tabs != null && tabIndex < tabs.getTabCount()) {
                tabs.setTitleAt(tabIndex, title);
            }
        });
    }
}

//package uiAdmin;
//
//import dao.BusDAO;
//import dao.ChauffeurDAO;
//import dao.VoyageDAO;
//import model.Admin;
//import model.Bus;
//import model.Chauffeur;
//import model.Voyage;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.sql.SQLException;
//import java.util.List;
//
//public class AdminManagementFrame extends JFrame {
//
//    private Admin admin;
//
//    private DefaultTableModel busModel;
//    private DefaultTableModel voyageModel;
//    private DefaultTableModel driverModel;
//
//    private JTable busTable;
//    private JTable voyageTable;
//    private JTable driverTable;
//
//    public AdminManagementFrame(Admin admin) {
//        this.admin = admin;
//
//        setTitle("Gestion Administrateur");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        JLabel title = new JLabel("Gestion Globale", JLabel.CENTER);
//        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
//        title.setBorder(new EmptyBorder(20, 10, 20, 10));
//
//        JTabbedPane tabs = new JTabbedPane();
//        tabs.addTab("Bus", createBusPanel());
//        tabs.addTab("Voyages", createVoyagePanel());
//        tabs.addTab("Conducteurs", createDriverPanel());
//
//        JButton backBtn = new JButton("Retour");
//        backBtn.addActionListener(e -> {
//            new AdminHomeFrame(admin);
//            dispose();
//        });
//
//        JPanel footer = new JPanel();
//        footer.add(backBtn);
//
//        add(title, BorderLayout.NORTH);
//        add(tabs, BorderLayout.CENTER);
//        add(footer, BorderLayout.SOUTH);
//
//        setVisible(true);
//    }
//
//    // ================= BUS =================
//    private JPanel createBusPanel() {
//        JPanel panel = new JPanel(new BorderLayout(10, 10));
//
//        busModel = new DefaultTableModel(new String[]{"ID", "Matricule", "Capacité"}, 0);
//        busTable = new JTable(busModel);
//
//        loadBusData();
//
//        JButton add = new JButton("Ajouter");
//        add.addActionListener(e -> new BusDialog(this));
//
//        JButton delete = new JButton("Supprimer");
//        delete.addActionListener(e -> deleteBus());
//
//        JButton refresh = new JButton("Actualiser");
//        refresh.addActionListener(e -> loadBusData());
//
//        JPanel top = new JPanel();
//        top.add(add);
//        top.add(delete);
//        top.add(refresh);
//
//        panel.add(top, BorderLayout.NORTH);
//        panel.add(new JScrollPane(busTable), BorderLayout.CENTER);
//        return panel;
//    }
//
//    private void loadBusData() {
//        busModel.setRowCount(0);
//        try {
//            for (Bus b : new BusDAO().findAll()) {
//                busModel.addRow(new Object[]{
//                        b.getIdBus(),
//                        b.getMatricule(),
//                        b.getCapacite()
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Erreur chargement Bus");
//        }
//    }
//
//    private void deleteBus() {
//        int row = busTable.getSelectedRow();
//        if (row == -1) {
//            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bus.");
//            return;
//        }
//
//        int confirm = JOptionPane.showConfirmDialog(
//                this, "Supprimer ce bus ?", "Confirmation",
//                JOptionPane.YES_NO_OPTION
//        );
//
//        if (confirm == JOptionPane.YES_OPTION) {
//            try {
//                int id = (int) busModel.getValueAt(row, 0);
//                new BusDAO().delete(id);
//                loadBusData();
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(this, "Erreur suppression Bus");
//            }
//        }
//    }
//
//    // ================= VOYAGES =================
//    private JPanel createVoyagePanel() {
//        JPanel panel = new JPanel(new BorderLayout(10, 10));
//
//        voyageModel = new DefaultTableModel(
//                new String[]{"ID", "Date", "Heure", "Prix"}, 0
//        );
//        voyageTable = new JTable(voyageModel);
//
//        loadVoyageData();
//
//        JButton add = new JButton("Ajouter");
//        add.addActionListener(e -> new VoyageDialog(this));
//
//        JButton delete = new JButton("Supprimer");
//        delete.addActionListener(e -> deleteVoyage());
//
//        JButton refresh = new JButton("Actualiser");
//        refresh.addActionListener(e -> loadVoyageData());
//
//        JPanel top = new JPanel();
//        top.add(add);
//        top.add(delete);
//        top.add(refresh);
//
//        panel.add(top, BorderLayout.NORTH);
//        panel.add(new JScrollPane(voyageTable), BorderLayout.CENTER);
//        return panel;
//    }
//
//    private void loadVoyageData() {
//        voyageModel.setRowCount(0);
//        try {
//            for (Voyage v : new VoyageDAO().findAll()) {
//                voyageModel.addRow(new Object[]{
//                        v.getIdVoyage(),
//                        v.getDateVoyage(),
//                        v.getHeureDepart(),
//                        v.getPrix()
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Erreur chargement Voyages");
//        }
//    }
//
//    private void deleteVoyage() {
//        int row = voyageTable.getSelectedRow();
//        if (row == -1) {
//            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un voyage.");
//            return;
//        }
//
//        int confirm = JOptionPane.showConfirmDialog(
//                this, "Supprimer ce voyage ?", "Confirmation",
//                JOptionPane.YES_NO_OPTION
//        );
//
//        if (confirm == JOptionPane.YES_OPTION) {
//            try {
//                int id = (int) voyageModel.getValueAt(row, 0);
//                new VoyageDAO().delete(id);
//                loadVoyageData();
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(this, "Erreur suppression Voyage");
//            }
//        }
//    }
//
//    // ================= CONDUCTEURS =================
//    private JPanel createDriverPanel() {
//        JPanel panel = new JPanel(new BorderLayout(10, 10));
//
//        driverModel = new DefaultTableModel(
//                new String[]{"ID", "Nom", "Prénom", "Email", "Téléphone"}, 0
//        );
//        driverTable = new JTable(driverModel);
//
//        loadDriverData();
//
//        JButton add = new JButton("Ajouter");
//        add.addActionListener(e -> new DriverDialog(this));
//
//        JButton delete = new JButton("Supprimer");
//        delete.addActionListener(e -> deleteDriver());
//
//        JButton refresh = new JButton("Actualiser");
//        refresh.addActionListener(e -> loadDriverData());
//
//        JPanel top = new JPanel();
//        top.add(add);
//        top.add(delete);
//        top.add(refresh);
//
//        panel.add(top, BorderLayout.NORTH);
//        panel.add(new JScrollPane(driverTable), BorderLayout.CENTER);
//        return panel;
//    }
//
//    private void loadDriverData() {
//        driverModel.setRowCount(0);
//        try {
//            for (Chauffeur c : new ChauffeurDAO().findAll()) {
//                driverModel.addRow(new Object[]{
//                        c.getIdChauffeur(),
//                        c.getNom(),
//                        c.getPrenom(),
//                        c.getEmail(),
//                        c.getTelephone()
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Erreur chargement Conducteurs");
//        }
//    }
//
//    private void deleteDriver() {
//        int row = driverTable.getSelectedRow();
//        if (row == -1) {
//            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un conducteur.");
//            return;
//        }
//
//        int confirm = JOptionPane.showConfirmDialog(
//                this, "Supprimer ce conducteur ?", "Confirmation",
//                JOptionPane.YES_NO_OPTION
//        );
//
//        if (confirm == JOptionPane.YES_OPTION) {
//            try {
//                int id = (int) driverModel.getValueAt(row, 0);
//                new ChauffeurDAO().delete(id);
//                loadDriverData();
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(this, "Erreur suppression Conducteur");
//            }
//        }
//    }
//}
//
