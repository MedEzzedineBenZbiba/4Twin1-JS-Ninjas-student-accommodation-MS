package tn.esprit.tpfoyer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.tpfoyer.Foyer;

import java.util.List;
import java.util.Map;

public interface IFoyerService {
    public List<Foyer> retrieveAllFoyers();
    public Foyer retrieveFoyer(Long foyerId);
    public Foyer addFoyer(Foyer foyer);
    public void removeFoyer(Long foyerId);
    public Foyer modifyFoyer(Foyer foyer);
    Page<Foyer> searchFoyers(String keyword, String sortBy, String sortDir, Pageable pageable);
     Map<String, Object> getFoyerDetails(Long id, String lang);
}
