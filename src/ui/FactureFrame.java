//package ui;
//
//import model.Client;
//import model.Voyage;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.time.LocalDate;
//
//public class FactureFrame extends JFrame {
//
//    public FactureFrame(Client client, Voyage voyage) {
//
//        setTitle("Facture de réservation");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        JPanel background = new JPanel(new GridBagLayout());
//        background.setBackground(Color.LIGHT_GRAY);
//        add(background);
//
//        JPanel card = new JPanel(new GridBagLayout());
//        card.setBackground(Color.WHITE);
//        card.setBorder(new EmptyBorder(30, 40, 30, 40));
//        card.setPreferredSize(new Dimension(600, 450));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        JLabel title = new JLabel("FACTURE DE RÉSERVATION", SwingConstants.CENTER);
//        title.setFont(new Font("Arial", Font.BOLD, 24));
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        card.add(title, gbc);
//
//        gbc.gridwidth = 1;
//        gbc.gridy++;
//
//        addRow(card, gbc, "Nom :", client.getNom());
//        addRow(card, gbc, "Prénom :", client.getPrenom());
//        addRow(card, gbc, "Email :", client.getEmail());
//
//        addRow(card, gbc, "ID Voyage :", String.valueOf(voyage.getIdVoyage()));
//        addRow(card, gbc, "Date voyage :", voyage.getDateVoyage().toString());
//        addRow(card, gbc, "Heure départ :", voyage.getHeureDepart().toString());
//        addRow(card, gbc, "Date réservation :", LocalDate.now().toString());
//        addRow(card, gbc, "Prix :", voyage.getPrix() + " DA");
//
//        JButton backBtn = new JButton("Retour à l'accueil");
//        gbc.gridx = 0;
//        gbc.gridy++;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        card.add(backBtn, gbc);
//
//        backBtn.addActionListener(e -> {
//            new HomeFrame(client);
//            this.dispose();
//        });
//
//        background.add(card);
//        setVisible(true);
//    }
//
//    private void addRow(JPanel panel, GridBagConstraints gbc, String label, String value) {
//        gbc.gridx = 0;
//        panel.add(new JLabel(label), gbc);
//
//        gbc.gridx = 1;
//        JTextField field = new JTextField(value);
//        field.setEditable(false);
//        panel.add(field, gbc);
//
//        gbc.gridy++;
//    }
//}
//
//

package ui;

import model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;

public class FactureFrame extends JFrame {
    public FactureFrame(Client client, Voyage voyage) {
        setTitle("VoyageEZ - Facture");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(18, 18, 18));

        JPanel paper = new JPanel();
        paper.setLayout(new BoxLayout(paper, BoxLayout.Y_AXIS));
        paper.setBackground(Color.WHITE);
        paper.setBorder(new EmptyBorder(40, 50, 40, 50));
        paper.setPreferredSize(new Dimension(450, 550));

        JLabel header = new JLabel("CONFIRMATION", 0);
        header.setFont(new Font("Serif", Font.BOLD, 22));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        paper.add(header);
        paper.add(new JSeparator());
        paper.add(Box.createRigidArea(new Dimension(0, 20)));

        addInfo(paper, "Client", client.getPrenom() + " " + client.getNom());
        addInfo(paper, "Email", client.getEmail());
        paper.add(Box.createRigidArea(new Dimension(0, 10)));
        addInfo(paper, "Voyage ID", "#" + voyage.getIdVoyage());
        addInfo(paper, "Départ", voyage.getHeureDepart().toString());
        addInfo(paper, "Date", voyage.getDateVoyage().toString());
        paper.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel total = new JLabel("TOTAL : " + voyage.getPrix() + " DA");
        total.setFont(new Font("Segoe UI", Font.BOLD, 20));
        total.setAlignmentX(Component.CENTER_ALIGNMENT);
        paper.add(total);

        JButton homeBtn = new JButton("Retour à l'accueil");
        homeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeBtn.addActionListener(e -> { new HomeFrame(client); dispose(); });
        
        paper.add(Box.createRigidArea(new Dimension(0, 40)));
        paper.add(homeBtn);

        main.add(paper);
        add(main);
        setVisible(true);
    }

    private void addInfo(JPanel p, String key, String val) {
        JLabel l = new JLabel(key + " : " + val);
        l.setFont(new Font("Monospaced", Font.PLAIN, 14));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
    }
}
