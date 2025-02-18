package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Etudiant;

import java.util.List;

public interface IEtudiantService {
    public List<Etudiant> retrieveAllEtudiants();
    public Etudiant retrieveEtudiant(Long etudiantId);
    public Etudiant addEtudiant(Etudiant etudiant);
    public void removeEtudiant(Long etudiantId);
    public Etudiant modifyEtudiant(Etudiant etudiant);
}
