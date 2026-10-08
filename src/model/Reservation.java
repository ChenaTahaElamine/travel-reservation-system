package model;

import java.time.LocalDate;

public class Reservation {

    private Client client;
    private Voyage voyage;
    private LocalDate dateReservation;

    public Reservation() {}

    public Reservation(Client client, Voyage voyage, LocalDate dateReservation) {
        this.client = client;
        this.voyage = voyage;
        this.dateReservation = dateReservation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }
}