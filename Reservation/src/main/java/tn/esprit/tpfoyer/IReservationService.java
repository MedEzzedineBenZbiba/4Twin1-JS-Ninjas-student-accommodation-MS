package tn.esprit.tpfoyer;

import tn.esprit.tpfoyer.Reservation;

import java.util.List;

public interface IReservationService {
    public List<Reservation> retreiveAllReservations();
    public Reservation retrieveReservation(Integer reservationId);
    public Reservation addReservation(Reservation reservation);
    public void removeReservation(Integer reservationId);
    public Reservation modifyReservation(Reservation reservation);

}
