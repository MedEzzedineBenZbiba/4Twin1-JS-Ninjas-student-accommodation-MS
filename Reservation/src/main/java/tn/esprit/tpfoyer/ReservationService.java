package tn.esprit.tpfoyer;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationService implements IReservationService{
    ReservationRepository reservationRepository;
    EmailService emailService;
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


    @Scheduled(cron = "0 */2 * * * *")
    public void remindStudentsToConfirmReservation() {
        List<Reservation> invalidReservations = reservationRepository.findAllByEstValide(false);

        for (Reservation r : invalidReservations ) {
            log.info("repo" + r);
            try {
                emailService.sendEmail("benzbibaezzdine@gmail.com", "pay reminder" , true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
