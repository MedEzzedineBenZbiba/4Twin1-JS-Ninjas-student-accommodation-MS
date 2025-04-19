package tn.esprit.tpfoyer;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservation;
    private Date anneeUniversitaire;
    private boolean estValide;


}
