

package uiAdmin;

import dao.BusDAO;
import dao.ChauffeurDAO;
import model.Bus;
import model.Chauffeur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BusDialog extends JDialog {

    private JComboBox<Chauffeur> chauffeurCombo;

    public BusDialog(JFrame parent) {
        super(parent, "Ajouter Bus", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField matriculeField = new JTextField();
        JTextField capaciteField = new JTextField();

        chauffeurCombo = new JComboBox<>();
        loadChauffeurs();

        JButton saveBtn = new JButton("Enregistrer");

        saveBtn.addActionListener(e -> saveBus(matriculeField, capaciteField));

        panel.add(new JLabel("Matricule"));
        panel.add(matriculeField);

        panel.add(new JLabel("Capacité"));
        panel.add(capaciteField);

        panel.add(new JLabel("Chauffeur"));
        panel.add(chauffeurCombo);

        panel.add(new JLabel());
        panel.add(saveBtn);

        add(panel);
        setVisible(true);
    }

    // ================= CHARGEMENT CHAUFFEURS =================
    private void loadChauffeurs() {
        try {
            List<Chauffeur> chauffeurs = new ChauffeurDAO().findAll();
            for (Chauffeur c : chauffeurs) {
                chauffeurCombo.addItem(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement des chauffeurs",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= ENREGISTREMENT BUS =================
    private void saveBus(JTextField matriculeField, JTextField capaciteField) {
        try {
            String matricule = matriculeField.getText();
            int capacite = Integer.parseInt(capaciteField.getText());
            Chauffeur chauffeur = (Chauffeur) chauffeurCombo.getSelectedItem();

            if (chauffeur == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un chauffeur");
                return;
            }

            Bus bus = new Bus();
            bus.setMatricule(matricule);
            bus.setCapacite(capacite);
            bus.setChauffeur(chauffeur); // relation père → fils

            new BusDAO().insert(bus);

            JOptionPane.showMessageDialog(this, "Bus ajouté avec succès");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de l'enregistrement du bus",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

