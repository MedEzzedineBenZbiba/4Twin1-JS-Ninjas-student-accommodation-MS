package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Bloc;

import java.util.List;


public interface IBlocService {
    public List<Bloc> retrieveAllBlocs();
    public Bloc retrieveBloc(Long idBloc);
    public Bloc addBloc(Bloc bloc);
    public void removeBloc(Long IdBloc);
    public Bloc modifyBloc(Bloc bloc);
    public Bloc AddBlocAndFoyerWithAffectation(Bloc bloc);
    public Bloc AffectBlocToFoyer(Long idFoyer, Long idBloc);
    public Bloc desacffectBlocFromFoyer(Long idBloc);
    public List<Bloc> retrieveBlocsSansFoyer();

}
