package tn.esprit.tpfoyer;

import lombok.Data;

@Data
public class UniversiteWithFoyerDTO {
    private Long idUniversite;
    private String nomUniversite;
    private String adresse;
    private FoyerDTO foyer;
}