package tn.esprit.tpfoyer.dto;

import lombok.Data;

@Data
public class UniversiteWithFoyerDTO {
    private Long idUniversite;
    private String nomUniversite;
    private String adresse;
    private Foyer foyer;
}