//package ui;
//
//import dao.ClientDAO;
//import model.Client;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.SQLException;
//
//public class AccountSettingsFrame extends JFrame {
//
//    public AccountSettingsFrame(Client client) {
//        setTitle("Paramètres du compte");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        // Panel principal
//        JPanel bg = new JPanel(new GridBagLayout());
//        bg.setBackground(Color.GRAY);
//        add(bg);
//
//        // Formulaire
//        JPanel form = new JPanel(new GridBagLayout());
//        form.setBackground(new Color(173, 216, 230));
//        form.setPreferredSize(new Dimension(500, 350));
//        bg.add(form);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Champs
//        JTextField nomField = new JTextField(client.getNom());
//        JTextField prenomField = new JTextField(client.getPrenom());
//        JTextField telephonField = new JTextField(client.getTelephone());
//        JTextField emailField = new JTextField(client.getEmail());
//        JPasswordField passwordField = new JPasswordField(client.getPassword());
//
//        // Ajouter tous les champs
//        addField(form, gbc, 0, "Nom :", nomField);
//        addField(form, gbc, 1, "Prénom :", prenomField);
//        addField(form, gbc, 2, "Téléphone :", telephonField);
//        addField(form, gbc, 3, "Email :", emailField);
//        addField(form, gbc, 4, "Mot de passe :", passwordField);
//
//        // Boutons
//        JButton saveBtn = new JButton("Confirmer");
//        JButton deleteBtn = new JButton("Supprimer");
//        JButton backBtn = new JButton("Retour");
//
//        // Panel pour les boutons
//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
//        btnPanel.setBackground(new Color(173, 216, 230));
//        btnPanel.add(saveBtn);
//        btnPanel.add(deleteBtn);
//        btnPanel.add(backBtn);
//
//        // Ajouter le panel des boutons en dessous des champs (y=5)
//        gbc.gridx = 0;
//        gbc.gridy = 5;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        form.add(btnPanel, gbc);
//
//        // Actions des boutons
//        saveBtn.addActionListener(e -> {
//            client.setNom(nomField.getText());
//            client.setPrenom(prenomField.getText());
//            client.setTelephone(telephonField.getText());
//            client.setEmail(emailField.getText());
//            client.setPassword(new String(passwordField.getPassword()));
//            try {
//                new ClientDAO().update(client);
//                JOptionPane.showMessageDialog(this, "Modifications enregistrées");
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour", "Erreur",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        deleteBtn.addActionListener(e -> {
//            int res = JOptionPane.showConfirmDialog(this,
//                    "Confirmer la suppression ?", "Attention",
//                    JOptionPane.YES_NO_OPTION);
//            if (res == JOptionPane.YES_OPTION) {
//                try {
//                    new ClientDAO().delete(client.getIdClient());
//                    new LoginFrame();
//                    this.dispose();
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression", "Erreur",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//
//        backBtn.addActionListener(e -> {
//            new HomeFrame(client);
//            this.dispose();
//        });
//
//        setVisible(true);
//    }
//
//    // Méthode utilitaire pour ajouter Label + Champ
//    private void addField(JPanel panel, GridBagConstraints gbc, int y,
//                          String labelText, JTextField field) {
//        gbc.gridx = 0;
//        gbc.gridy = y;
//        gbc.weightx = 0;
//        gbc.gridwidth = 1;
//        panel.add(new JLabel(labelText), gbc);
//
//        gbc.gridx = 1;
//        gbc.weightx = 1;
//        field.setPreferredSize(new Dimension(250, 30));
//        panel.add(field, gbc);
//    }
//}
//
//
//
////
////package ui;
////
////import dao.ClientDAO;
////import model.Client;
////
////import javax.swing.*;
////import java.awt.*;
////import java.sql.SQLException;
////
////public class AccountSettingsFrame extends JFrame {
////
////    public AccountSettingsFrame(Client client) {
////        setTitle("Paramètres du compte");
////        setExtendedState(JFrame.MAXIMIZED_BOTH);
////
////        JPanel bg = new JPanel(new GridBagLayout());
////        bg.setBackground(Color.GRAY);
////
////        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
////        form.setBackground(new Color(173, 216, 230));
////
////        JTextField nom = new JTextField(client.getNom());
////        JTextField prenom = new JTextField(client.getPrenom());
////        JTextField email = new JTextField(client.getEmail());
////
////        JButton saveBtn = new JButton("Confirmer");
////        JButton deleteBtn = new JButton("Supprimer");
////        JButton backBtn = new JButton("Retour");
////
////        form.add(new JLabel("Nom"));
////        form.add(nom);
////        form.add(new JLabel("Prénom"));
////        form.add(prenom);
////        form.add(new JLabel("Email"));
////        form.add(email);
////        form.add(saveBtn);
////        form.add(deleteBtn);
////        form.add(backBtn);
////
////        bg.add(form);
////        add(bg);
////
////        saveBtn.addActionListener(e -> {
////            client.setNom(nom.getText());
////            client.setPrenom(prenom.getText());
////            client.setEmail(email.getText());
////            try {
////				new ClientDAO().update(client);
////			} catch (SQLException e1) {
////				// TODO Auto-generated catch block
////				e1.printStackTrace();
////			}
////            JOptionPane.showMessageDialog(this, "Modifications enregistrées");
////        });
////
////        deleteBtn.addActionListener(e -> {
////            int res = JOptionPane.showConfirmDialog(this,
////                    "Confirmer la suppression ?", "Attention",
////                    JOptionPane.YES_NO_OPTION);
////
////            if (res == JOptionPane.YES_OPTION) {
////                try {
////					new ClientDAO().delete(client.getIdClient());
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////                new LoginFrame();
////                this.dispose();
////                
////            }
////        });
////
////        backBtn.addActionListener(e -> {
////        	new HomeFrame(client);
////        	this.dispose();
////            
////        });
////
////        setVisible(true);
////    }
////}
////
////


package ui;

import dao.ClientDAO;
import model.Client;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.SQLException;

public class AccountSettingsFrame extends JFrame {
    private final Color COLOR_BG = new Color(18, 18, 18);
    private final Color COLOR_CARD = new Color(30, 30, 30);
    private final Color COLOR_TEXT = Color.WHITE;

    public AccountSettingsFrame(Client client) {
        setTitle("VoyageEZ - Paramètres");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(COLOR_BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_CARD);
        card.setBorder(new CompoundBorder(new LineBorder(new Color(50, 50, 50)), new EmptyBorder(30, 40, 30, 40)));
        card.setPreferredSize(new Dimension(500, 600));

        JLabel title = new JLabel("Modifier mon profil");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(COLOR_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // Champs
        JTextField nomField = createField(card, "NOM", client.getNom());
        JTextField prenomField = createField(card, "PRÉNOM", client.getPrenom());
        JTextField telField = createField(card, "TÉLÉPHONE", client.getTelephone());
        JTextField emailField = createField(card, "EMAIL", client.getEmail());
        JPasswordField passField = new JPasswordField(client.getPassword());
        addLabel(card, "MOT DE PASSE");
        styleComp(passField);
        card.add(passField);

        // Boutons
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(420, 40));

        JButton saveBtn = createBtn("Enregistrer", new Color(46, 204, 113));
        JButton backBtn = createBtn("Retour", new Color(70, 70, 70));
        JButton deleteBtn = createBtn("Supprimer", new Color(231, 76, 60));

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);
        btnPanel.add(deleteBtn);
        
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(btnPanel);

        // Actions
        saveBtn.addActionListener(e -> {
            client.setNom(nomField.getText());
            client.setPrenom(prenomField.getText());
            client.setTelephone(telField.getText());
            client.setEmail(emailField.getText());
            client.setPassword(new String(passField.getPassword()));
            try {
                new ClientDAO().update(client);
                JOptionPane.showMessageDialog(this, "Profil mis à jour !");
            } catch (SQLException ex) { ex.printStackTrace(); }
        });

        backBtn.addActionListener(e -> { new HomeFrame(client); dispose(); });
        
        deleteBtn.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this, "Supprimer définitivement ?", "Attention", 0) == 0) {
                try { new ClientDAO().delete(client.getIdClient()); new LoginFrame(); dispose(); } 
                catch (SQLException ex) { ex.printStackTrace(); }
            }
        });

        mainPanel.add(card);
        add(mainPanel);
        setVisible(true);
    }

    private JTextField createField(JPanel p, String lbl, String val) {
        addLabel(p, lbl);
        JTextField f = new JTextField(val);
        styleComp(f);
        p.add(f);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        return f;
    }

    private void addLabel(JPanel p, String txt) {
        JLabel l = new JLabel(txt);
        l.setForeground(new Color(150, 150, 150));
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        p.add(l);
    }

    private void styleComp(JComponent c) {
        c.setMaximumSize(new Dimension(420, 35));
        c.setBackground(new Color(45, 45, 45));
        c.setForeground(Color.WHITE);
        c.setBorder(new CompoundBorder(new LineBorder(new Color(70, 70, 70)), new EmptyBorder(0, 10, 0, 10)));
    }

    private JButton createBtn(String txt, Color bg) {
        JButton b = new JButton(txt);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}