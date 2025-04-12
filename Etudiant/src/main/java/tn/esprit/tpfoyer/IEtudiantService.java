package tn.esprit.tpfoyer;

import tn.esprit.tpfoyer.Etudiant;

import java.util.List;

public interface IEtudiantService {
    public List<Etudiant> retrieveAllEtudiants();
    public Etudiant retrieveEtudiant(Long etudiantId);
    public Etudiant addEtudiant(Etudiant etudiant);
    public void removeEtudiant(Long etudiantId);
    public Etudiant modifyEtudiant(Etudiant etudiant);
    List<Etudiant> findEtudiantsByeEcole(String ecole);
}
