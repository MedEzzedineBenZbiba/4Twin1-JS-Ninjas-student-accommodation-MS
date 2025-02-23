package tn.esprit.tpfoyer;

import tn.esprit.tpfoyer.Reservation;

import java.util.List;

public interface IReservationService {
    public List<Reservation> retreiveAllReservations();
    public Reservation retrieveReservation(String reservationId);
    public Reservation addReservation(Reservation reservation);
    public void removeReservation(String reservationId);
    public Reservation modifyReservation(Reservation reservation);

}
