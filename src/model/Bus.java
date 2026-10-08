package model;

public class Bus {

    private int idBus;
    private String matricule;
    private int capacite;
    private Chauffeur chauffeur;

    public Bus() {}

    public Bus(int idBus, String matricule, int capacite, Chauffeur chauffeur) {
        this.idBus = idBus;
        this.matricule = matricule;
        this.capacite = capacite;
        this.chauffeur = chauffeur;
    }

    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }
    
    @Override
    public String toString() {
        return "Bus ID " + idBus + " - " + matricule;
    }

}
