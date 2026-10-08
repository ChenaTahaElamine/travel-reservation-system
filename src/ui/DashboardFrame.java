package ui;

import model.Client;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private Client client;

    public DashboardFrame(Client client) {

        this.client = client;

        setTitle("Espace Client");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =========================
        // Fond gris
        // =========================
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.GRAY);
        add(background);

        // =========================
        // Conteneur central
        // =========================
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setPreferredSize(new Dimension(450, 300));
        centerPanel.setBackground(new Color(173, 216, 230)); // bleu clair

        background.add(centerPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // =========================
        // Label central
        // =========================
        JLabel welcomeLabel = new JLabel(
                "Bienvenue " + client.getNom() + " " + client.getPrenom()
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(welcomeLabel, gbc);

        // =========================
        // Bouton Réservation
        // =========================
        gbc.gridy++;
        gbc.gridwidth = 1;

        JButton reservationButton = new JButton("Réserver un voyage");
        reservationButton.addActionListener(e -> openReservationPage());
        centerPanel.add(reservationButton, gbc);

        // =========================
        // Bouton Paramètres
        // =========================
        gbc.gridx = 1;

        JButton settingsButton = new JButton("Paramètres du compte");
        settingsButton.addActionListener(e -> openSettingsPage());
        centerPanel.add(settingsButton, gbc);

        setVisible(true);
    }

    // =========================
    // Navigation
    // =========================
    private void openReservationPage() {
        JOptionPane.showMessageDialog(this,
                "Page de réservation à implémenter",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);

        // Exemple futur :
        // dispose();
        // new ReservationFrame(client);
    }
    
    private void openSettingsPage() {
        dispose();
        new AccountSettingsFrame(this.client);
    }


//    private void openSettingsPage() {
//        JOptionPane.showMessageDialog(this,
//                "Paramètres du compte à implémenter",
//                "Information",
//                JOptionPane.INFORMATION_MESSAGE);
//
//        // Exemple futur :
//        // dispose();
//        // new AccountSettingsFrame(client);
//    }
}
