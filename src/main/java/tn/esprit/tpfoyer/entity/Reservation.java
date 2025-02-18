package tn.esprit.tpfoyer.entity;

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
    private String idReservation;
    private Date anneeUniversitaire;
    private boolean estValide;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Etudiant> etudiants;
}
