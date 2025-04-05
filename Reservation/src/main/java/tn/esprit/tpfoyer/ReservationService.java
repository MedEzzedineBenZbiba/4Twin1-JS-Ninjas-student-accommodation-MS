package tn.esprit.tpfoyer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationService implements IReservationService{
    ReservationRepository reservationRepository;
    @Override
    public List<Reservation> retreiveAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation retrieveReservation(Integer reservationId) {
        return reservationRepository.findById(reservationId).get();
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void removeReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public Reservation modifyReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> retreiveAllReservationsByValidity(boolean b) {
        return reservationRepository.findAllByEstValide(b);
    }

    public List<Reservation> getAllReservationsOrderedByDateDesc() {
        return reservationRepository.findAllOrderedByDateDesc();
    }

    @Override
    public Integer ReservationsNumberBetweenDates(Date startDate, Date endDate) {
        return reservationRepository.ReservationsNumberBetweenDates(startDate, endDate);
    }

    @Override
    public List<Reservation> findAllOrderedByDateDesc() {
        return reservationRepository.findAllOrderedByDateDesc();
    }


    @Scheduled(fixedRate = 50000)
    public void mettreAJourEtAffciherReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        String string = "01-01-2024";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate localDate = LocalDate.parse(string, formatter);
        java.util.Date d = java.sql.Date.valueOf(localDate);

        for(Reservation r : reservations) {
            if(r.getAnneeUniversitaire().before(d)) {
                r.setEstValide(false);
                reservationRepository.save(r);
            }
            log.info(("repo" + r));

        }
    }
}
