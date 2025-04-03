package tn.esprit.tpfoyer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
@Tag(name = "Gestion reservation")
public class ReservationRestController {
    IReservationService reservationService;

    @Operation(description = "récupérer toutes les reservations de la base de données")
    @GetMapping
    public List<Reservation> getAllReservations(){
        return reservationService.retreiveAllReservations();
    }

    @Operation(description = "récupérer une reservation par id de la base de données")
    @GetMapping("/{reservation-id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("reservation-id") Integer reservationId){
        try {
            Reservation r = reservationService.retrieveReservation(reservationId);
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Reservation r = reservationService.retrieveReservation(reservationId);
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @Operation(description = "Ajouter une reservation ")
    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation){
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
