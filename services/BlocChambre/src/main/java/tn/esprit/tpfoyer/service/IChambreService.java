package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;

import java.util.List;

public interface IChambreService {
    public List<Chambre> retrieveAllChambres();
    public Chambre retrieveChambre(Long chambreId);
    public Chambre addChambre(Chambre c);
    public void removeChambre(Long chambreId);
    public Chambre modifyChambre(Chambre chambre);
    public List<Chambre> retriveAllChambresByTypeChambre(TypeChambre typeChambre);
    public Chambre retrieveChambreByNumberChambre(Long numC);
    public Chambre retrieveChambreByEtudiantCin(Long cin);
}
