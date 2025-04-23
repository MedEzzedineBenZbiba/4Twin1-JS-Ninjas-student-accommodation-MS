package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.dto.UniversiteWithFoyerDTO;
import tn.esprit.tpfoyer.entity.Universite;

import java.util.List;
import java.util.Map;

public interface IUniversiteService {
     List<Universite> retreiveAllUniversities();
     Universite retrieveUniversity(Long universityId);
     Universite addUniversity(Universite universite);
     void removeUniversity(Long universityId);
     Universite modifyUniversity(Universite universite);
    Universite affectFoyerToUniversite(Long UniversiteId, Long foyerId);
    Universite desaffectFoyerFromUniversite(Long universeId);
     UniversiteWithFoyerDTO getFoyerByNomUniversite(String nomUniversite);
     Map<String, Long> getUniversitesCountByAdresse();
}
