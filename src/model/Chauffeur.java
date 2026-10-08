package model;

public class Chauffeur {

    private int idChauffeur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    public Chauffeur() {}

    public Chauffeur(int idChauffeur, String nom, String prenom,
                     String email, String telephone) {
        this.idChauffeur = idChauffeur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public int getIdChauffeur() {
        return idChauffeur;
    }

    public void setIdChauffeur(int idChauffeur) {
        this.idChauffeur = idChauffeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Override
    public String toString() {
        return "ID " + idChauffeur + " - " + nom + " " + prenom;
    }

}