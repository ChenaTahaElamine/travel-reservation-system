//package ui;
//
//import dao.ReservationDAO;
//import dao.VoyageDAO;
//import model.Client;
//import model.Voyage;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.sql.SQLException;
//import java.util.List;
//
//public class ReservationFrame extends JFrame {
//
//    private JTable table;
//    private JTextArea details;
//    private List<Voyage> voyages;
//    private Client client;
//
//    public ReservationFrame(Client client) throws SQLException {
//        this.client = client;
//
//        setTitle("Réservation de voyage");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        voyages = new VoyageDAO().findAll();
//
//        JPanel background = new JPanel(new BorderLayout(20, 20));
//        background.setBackground(Color.GRAY);
//        background.setBorder(new EmptyBorder(20, 20, 20, 20));
//        add(background);
//
//        // ===== TITRE =====
//        JLabel title = new JLabel("Choisissez votre voyage", SwingConstants.CENTER);
//        title.setFont(new Font("Arial", Font.BOLD, 26));
//        title.setForeground(Color.WHITE);
//        background.add(title, BorderLayout.NORTH);
//
//        // ===== TABLE =====
//        String[] cols = {"ID", "Heure départ", "Prix (DA)"};
//        Object[][] data = new Object[voyages.size()][3];
//
//        for (int i = 0; i < voyages.size(); i++) {
//            Voyage v = voyages.get(i);
//            data[i][0] = v.getIdVoyage();
//            data[i][1] = v.getHeureDepart();
//            data[i][2] = v.getPrix();
//        }
//
//        table = new JTable(data, cols);
//        table.setRowHeight(28);
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        JScrollPane tableScroll = new JScrollPane(table);
//        tableScroll.setPreferredSize(new Dimension(500, 0));
//
//        // ===== DÉTAILS =====
//        details = new JTextArea();
//        details.setEditable(false);
//        details.setFont(new Font("Monospaced", Font.PLAIN, 14));
//        details.setBorder(new EmptyBorder(15, 15, 15, 15));
//
//        JPanel detailsPanel = new JPanel(new BorderLayout(10, 10));
//        detailsPanel.setBackground(new Color(173, 216, 230));
//        detailsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
//
//        JLabel detailsTitle = new JLabel("Détails du voyage");
//        detailsTitle.setFont(new Font("Arial", Font.BOLD, 18));
//        detailsPanel.add(detailsTitle, BorderLayout.NORTH);
//        detailsPanel.add(new JScrollPane(details), BorderLayout.CENTER);
//
//        // ===== BOUTONS =====
//        JButton reserveBtn = new JButton("Réserver");
//        JButton backBtn = new JButton("Retour");
//
//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//        btnPanel.setBackground(new Color(173, 216, 230));
//        btnPanel.add(reserveBtn);
//        btnPanel.add(backBtn);
//
//        detailsPanel.add(btnPanel, BorderLayout.SOUTH);
//
//        // ===== ACTIONS =====
//        table.getSelectionModel().addListSelectionListener(e -> {
//            int row = table.getSelectedRow();
//            if (row >= 0) {
//                Voyage v = voyages.get(row);
//                details.setText(
//                        "ID Voyage : " + v.getIdVoyage() + "\n" +
//                        "Date : " + v.getDateVoyage() + "\n" +
//                        "Heure départ : " + v.getHeureDepart() + "\n" +
//                        "Prix : " + v.getPrix() + " DA"
//                );
//            }
//        });
//
//        reserveBtn.addActionListener(e -> reserveVoyage());
//        backBtn.addActionListener(e -> {
//            new HomeFrame(client);
//            this.dispose();
//        });
//
//        background.add(tableScroll, BorderLayout.WEST);
//        background.add(detailsPanel, BorderLayout.CENTER);
//
//        setVisible(true);
//    }
//
//    private void reserveVoyage() {
//        int row = table.getSelectedRow();
//        if (row < 0) {
//            JOptionPane.showMessageDialog(this,
//                    "Veuillez sélectionner un voyage",
//                    "Attention",
//                    JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        try {
//            Voyage selectedVoyage = voyages.get(row);
//            new ReservationDAO().createReservation(
//                    client.getIdClient(),
//                    selectedVoyage.getIdVoyage()
//            );
//
//            new FactureFrame(client, selectedVoyage);
//            this.dispose();
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this,
//                    "Erreur lors de la réservation",
//                    "Erreur",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}
//


package ui;

import dao.*;
import model.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationFrame extends JFrame {
    private JTable table;
    private JTextArea detailsArea;
    private List<Voyage> voyages;

    public ReservationFrame(Client client) throws java.sql.SQLException {
        setTitle("VoyageEZ - Réservations");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(18, 18, 18));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Liste des voyages (Gauche)
        voyages = new VoyageDAO().findAll();
        String[] cols = {"ID", "Heure Départ", "Prix (DA)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (Voyage v : voyages) model.addRow(new Object[]{v.getIdVoyage(), v.getHeureDepart(), v.getPrix()});

        table = new JTable(model);
        table.setRowHeight(35);
        table.setBackground(new Color(30, 30, 30));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(50, 50, 50));
        table.setSelectionBackground(new Color(0, 120, 215));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(new Color(50, 50, 50)));

        // Détails (Droite)
        JPanel detailsPanel = new JPanel(new BorderLayout(15, 15));
        detailsPanel.setBackground(new Color(30, 30, 30));
        detailsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        detailsPanel.setPreferredSize(new Dimension(400, 0));

        detailsArea = new JTextArea("Veuillez sélectionner un voyage...");
        detailsArea.setEditable(false);
        detailsArea.setBackground(new Color(40, 40, 40));
        detailsArea.setForeground(Color.WHITE);
        detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsArea.setMargin(new Insets(10, 10, 10, 10));

        JButton resBtn = new JButton("Confirmer la réservation");
        resBtn.setBackground(new Color(46, 204, 113));
        resBtn.setForeground(Color.WHITE);
        resBtn.setPreferredSize(new Dimension(0, 50));
        resBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        detailsPanel.add(new JLabel("📝 DÉTAILS DU VOYAGE", 0), BorderLayout.NORTH);
        detailsPanel.add(detailsArea, BorderLayout.CENTER);
        detailsPanel.add(resBtn, BorderLayout.SOUTH);

        // Actions
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Voyage v = voyages.get(row);
                detailsArea.setText("\n 🚍 Voyage n° " + v.getIdVoyage() + "\n\n 📅 Date: " + v.getDateVoyage() + 
                                   "\n ⏰ Départ: " + v.getHeureDepart() + "\n 💰 Prix: " + v.getPrix() + " DA");
            }
        });

        resBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                try {
                    new ReservationDAO().createReservation(client.getIdClient(), voyages.get(row).getIdVoyage());
                    new FactureFrame(client, voyages.get(row));
                    dispose();
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(detailsPanel, BorderLayout.EAST);
        add(mainPanel);
        setVisible(true);
    }
}