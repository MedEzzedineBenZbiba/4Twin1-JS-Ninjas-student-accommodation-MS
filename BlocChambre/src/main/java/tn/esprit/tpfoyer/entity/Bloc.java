package tn.esprit.tpfoyer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloc;

    private String nomBloc;
    private Long capaciteBloc;
    private Double latitude;
    private Double longitude;
    private String adresse;

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL)
    private Set<Chambre> chambres;
}