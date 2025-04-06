package tn.esprit.tpfoyer;

import tn.esprit.tpfoyer.Universite;

import java.util.List;
import java.util.Map;

public interface IUniversiteService {
    public List<Universite> retreiveAllUniversities();
    public Universite retrieveUniversity(Long universityId);
    public Universite addUniversity(Universite universite);
    public void removeUniversity(Long universityId);
    public Universite modifyUniversity(Universite universite);
    public UniversiteWithFoyerDTO getFoyerByNomUniversite(String nomUniversite);
    public Map<String, Long> getUniversitesCountByAdresse();
}
