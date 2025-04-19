package tn.esprit.tpfoyer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/reservation")
@Tag(name = "Gestion reservation")
public class ReservationRestController {
    IReservationService reservationService;
    EmailService emailService;

    @Operation(description = "récupérer toutes les reservations de la base de données")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_reservation_client_admin')")
    public Flux<Reservation> getAllReservations(){
        return Flux.defer(() -> Flux.fromIterable(reservationService.retreiveAllReservations()));
    }


    @Operation(description = "Récupérer une reservation par ID de la base de données")
    @GetMapping("/{reservation-id}")
    public Mono<ResponseEntity<Reservation>> getReservation(@PathVariable("reservation-id") Integer reservationId) {
        return Mono.fromCallable(() -> reservationService.retrieveReservation(reservationId))
                .map(reservation -> ResponseEntity.ok(reservation)) // If reservation found
                .onErrorResume(e -> {
                    System.out.println("Error: " + e.getMessage());
                    return Mono.just(ResponseEntity.notFound().build()); // 404 if not found
                });
    }


    @Operation(description = "récupérer toutes les reservations par validiter")
    @GetMapping("ByValidity/{booleanValue}")
    public ResponseEntity<List<Reservation>> getReservation(@PathVariable("booleanValue") Boolean b){
       List<Reservation> reservationsByValidity = reservationService.retreiveAllReservationsByValidity(b);
        return ResponseEntity.status(HttpStatus.OK).body(reservationsByValidity);
    }

    @Operation(description = "récupérer nombre de réservations entre deux dates")
    @GetMapping("ByDate/{start}/{end}")
    public ResponseEntity<Integer> getReservation(
            @PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @PathVariable("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {

        Integer reservationsNumber = reservationService.ReservationsNumberBetweenDates(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(reservationsNumber);
    }

    @Operation(description = "Get all reservations ordered by date descending")
    @GetMapping("orderedByDateDesc")
    public ResponseEntity<List<Reservation>> getReservationsOrderedByDateDesc() {
        List<Reservation> reservations = reservationService.findAllOrderedByDateDesc();
        return ResponseEntity.ok(reservations);
    }


    @Operation(description = "Ajouter une reservation ")
    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation){
        try {
            emailService.sendEmail("benzbibaezzdine@gmail.com", "Reservation confirmed",  false);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reservationService.addReservation(reservation);
    }

    @Operation(description = "retirer une reservation par id")
    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("reservation-id") Integer reservationId){
        try {
            Reservation r = reservationService.retrieveReservation(reservationId);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is not a reservation with that id");
        }
        reservationService.removeReservation(reservationId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }

    @Operation(description = "mise à jour d'une reservation de la base de données")
    @PutMapping("/{reservation-id}")
    public ResponseEntity<Reservation> modifyReservation(@PathVariable("reservation-id") Integer reservationId , @RequestBody Reservation reservation){
        try {
            Reservation r = reservationService.retrieveReservation(reservationId);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        reservation.setIdReservation(reservationId);
        Reservation updatedReservation = reservationService.modifyReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(updatedReservation);

    }
}
