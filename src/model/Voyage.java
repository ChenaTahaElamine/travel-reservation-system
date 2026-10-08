package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Voyage {

    private int idVoyage;
    private LocalDate dateVoyage;
    private LocalTime heureDepart;
    private double prix;
    private Bus bus;

    public Voyage() {}

    public Voyage(int idVoyage, LocalDate dateVoyage,
                  LocalTime heureDepart, double prix, Bus bus) {
        this.idVoyage = idVoyage;
        this.dateVoyage = dateVoyage;
        this.heureDepart = heureDepart;
        this.prix = prix;
        this.bus = bus;
    }

    public int getIdVoyage() {
        return idVoyage;
    }

    public void setIdVoyage(int idVoyage) {
        this.idVoyage = idVoyage;
    }

    public LocalDate getDateVoyage() {
        return dateVoyage;
    }

    public void setDateVoyage(LocalDate dateVoyage) {
        this.dateVoyage = dateVoyage;
    }

    public LocalTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}