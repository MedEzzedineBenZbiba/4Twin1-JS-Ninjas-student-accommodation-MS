package tn.esprit.tpfoyer;

import tn.esprit.tpfoyer.Universite;

import java.util.List;

public interface IUniversiteService {
    public List<Universite> retreiveAllUniversities();
    public Universite retrieveUniversity(Long universityId);
    public Universite addUniversity(Universite universite);
    public void removeUniversity(Long universityId);
    public Universite modifyUniversity(Universite universite);
}
