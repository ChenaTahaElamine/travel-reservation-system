

import javax.swing.SwingUtilities;
import java.util.Scanner;

import ui.LoginFrame;
import uiAdmin.AdminLoginFrame;

public class Class_principale {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choix = "";

        // Boucle jusqu'à ce que l'utilisateur saisisse une valeur correcte
        while (true) {
            System.out.println("Veuillez choisir une option :");
            System.out.println("Administrateur : admin");
            System.out.println("Client        : usr");
            System.out.print("Votre saisie : ");

            choix = scanner.nextLine().trim().toLowerCase();

            if (choix.equals("admin")) {
                SwingUtilities.invokeLater(AdminLoginFrame::new);
                break; // sortie de la boucle
            } else if (choix.equals("usr")) {
                SwingUtilities.invokeLater(LoginFrame::new);
                break; // sortie de la boucle
            } else {
                System.out.println("❌ Saisie invalide ! Veuillez entrer 'admin' ou 'usr'.\n");
            }
        }

        scanner.close();
    }
}
