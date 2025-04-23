package tn.esprit.tpfoyer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

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
    @GetMapping("{reservation-id}")
    public Reservation getReservation(@PathVariable("reservation-id") Long reservationId){
        return reservationService.retrieveReservation(reservationId);
    }

    @Operation(description = "Ajouter une reservation ")
    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation){
        return reservationService.addReservation(reservation);
    }

    @Operation(description = "retirer une reservation par id")
    @DeleteMapping("{reservation-id}")
    public void deleteReservation(@PathVariable("reservation-id") Long reservationId){
        reservationService.removeReservation(reservationId);
    }

    @Operation(description = "mise à jour d'une reservation de la base de données")
    @PutMapping
    public Reservation modifyReservation(@RequestBody Reservation reservation){
        return reservationService.modifyReservation(reservation);
    }
}
