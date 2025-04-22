package tn.esprit.tpfoyer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UniversityServiceImpl implements IUniversiteService{
    UniversiteRepository universiteRepository;
    @Override
    public List<Universite> retreiveAllUniversities() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite retrieveUniversity(Long universityId) {
        return universiteRepository.findById(universityId).get();
    }

    @Override
    public Universite addUniversity(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public void removeUniversity(Long universityId) {
        universiteRepository.deleteById(universityId);
    }

    @Override
    public Universite modifyUniversity(Universite universite) {
        return universiteRepository.save(universite);
    }


    private final FoyerClient foyerClient;

    public UniversiteWithFoyerDTO getFoyerByNomUniversite(String nomUniversite) {
        // 1. Trouver l'université par son nom
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite)
                .orElseThrow(() -> new RuntimeException("Université non trouvée"));

        // 2. Appeler le microservice Foyer pour récupérer le foyer
        FoyerDTO foyerDTO = foyerClient.getFoyerById(universite.getIdFoyer());

        // 3. Combiner les données
        return mapToUniversiteWithFoyerDTO(universite, foyerDTO);
    }

    private UniversiteWithFoyerDTO mapToUniversiteWithFoyerDTO(Universite universite, FoyerDTO foyerDTO) {
        UniversiteWithFoyerDTO dto = new UniversiteWithFoyerDTO();
        dto.setIdUniversite(universite.getIdUniversite());
        dto.setNomUniversite(universite.getNomUniversite());
        dto.setAdresse(universite.getAdresse());
        dto.setFoyer(foyerDTO);
        return dto;
    }


    public Map<String, Long> getUniversitesCountByAdresse() {
        List<Object[]> results = universiteRepository.countUniversitesByAdresse();

        Map<String, Long> stats = new HashMap<>();
        for (Object[] result : results) {
            String adresse = (String) result[0];
            Long count = (Long) result[1];
            stats.put(adresse, count);
        }

        return stats;
    }


}
