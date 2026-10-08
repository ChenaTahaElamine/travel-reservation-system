//
//
//package uiAdmin;
//
//import dao.BusDAO;
//import dao.VoyageDAO;
//import model.Voyage;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//public class VoyageDialog extends JDialog {
//
//    public VoyageDialog(JFrame parent) {
//        super(parent, "Ajouter Voyage", true);
//        setSize(400, 300);
//        setLocationRelativeTo(parent);
//
//        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
//        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
//
//        JTextField dateField = new JTextField("2026-01-01");
//        JTextField heureField = new JTextField("08:30");
//        JTextField prixField = new JTextField();
//
//        JButton saveBtn = new JButton("Enregistrer");
//
//        saveBtn.addActionListener(e -> {
//            try {
//                LocalDate date = LocalDate.parse(dateField.getText());
//                LocalTime heure = LocalTime.parse(heureField.getText());
//                double prix = Double.parseDouble(prixField.getText());
//
//                Voyage v = new Voyage();
//                v.setDateVoyage(date);
//                v.setHeureDepart(heure);
//                v.setPrix(prix);
//                v.setBus(new BusDAO().findById(1));
//
//                new VoyageDAO().insert(v);
//
//                JOptionPane.showMessageDialog(this, "Voyage ajouté avec succès");
//                dispose();
//
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Erreur de saisie.\nFormat attendu :\nDate : yyyy-MM-dd\nHeure : HH:mm",
//                        "Erreur",
//                        JOptionPane.ERROR_MESSAGE
//                );
//            }
//        });
//
//        panel.add(new JLabel("Date du voyage (yyyy-MM-dd)"));
//        panel.add(dateField);
//
//        panel.add(new JLabel("Heure départ (HH:mm)"));
//        panel.add(heureField);
//
//        panel.add(new JLabel("Prix (DA)"));
//        panel.add(prixField);
//
//        panel.add(new JLabel());
//        panel.add(saveBtn);
//
//        add(panel);
//        setVisible(true);
//    }
//}
//

package uiAdmin;

import dao.BusDAO;
import dao.VoyageDAO;
import model.Bus;
import model.Voyage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VoyageDialog extends JDialog {

    private JComboBox<Bus> busCombo;

    public VoyageDialog(JFrame parent) {
        super(parent, "Ajouter Voyage", true);
        setSize(420, 320);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField dateField = new JTextField(""+LocalDate.now());
        
        dateField.setEditable(false);

        JTextField heureField = new JTextField("08:30");
        JTextField prixField = new JTextField();

        busCombo = new JComboBox<>();
        loadBus();

        JButton saveBtn = new JButton("Enregistrer");

        saveBtn.addActionListener(e -> saveVoyage(dateField, heureField, prixField));

        panel.add(new JLabel("Date du voyage (yyyy-MM-dd)"));
        panel.add(dateField);

        panel.add(new JLabel("Heure départ (HH:mm)"));
        panel.add(heureField);

        panel.add(new JLabel("Prix (DA)"));
        panel.add(prixField);

        panel.add(new JLabel("Bus"));
        panel.add(busCombo);

        panel.add(new JLabel());
        panel.add(saveBtn);

        add(panel);
        setVisible(true);
    }

    // ================= CHARGEMENT BUS =================
    private void loadBus() {
        try {
            List<Bus> buses = new BusDAO().findAll();
            for (Bus b : buses) {
                busCombo.addItem(b);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement des bus",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= ENREGISTREMENT VOYAGE =================
    private void saveVoyage(JTextField dateField, JTextField heureField, JTextField prixField) {
        try {
            LocalDate date = LocalDate.parse(dateField.getText());
            LocalTime heure = LocalTime.parse(heureField.getText());
            double prix = Double.parseDouble(prixField.getText());

            Bus selectedBus = (Bus) busCombo.getSelectedItem();
            if (selectedBus == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bus");
                return;
            }

            Voyage v = new Voyage();
            v.setDateVoyage(date);
            v.setHeureDepart(heure);
            v.setPrix(prix);
            v.setBus(selectedBus); // relation père → fils

            new VoyageDAO().insert(v);

            JOptionPane.showMessageDialog(this, "Voyage ajouté avec succès");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur de saisie.\nFormats attendus :\nDate : yyyy-MM-dd\nHeure : HH:mm",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

