package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Reservation;

import java.util.List;

public interface IReservationService {
     List<Reservation> retreiveAllReservations();
     Reservation retrieveReservation(Long reservationId);
     Reservation addReservation(Reservation reservation);
     void removeReservation(Long reservationId);
     Reservation modifyReservation(Reservation reservation);

}
