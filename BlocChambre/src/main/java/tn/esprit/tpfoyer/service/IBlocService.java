package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Bloc;

import java.util.List;
import java.util.Map;


public interface IBlocService {
     List<Bloc> retrieveAllBlocs();
     Bloc retrieveBloc(Long idBloc);
     Bloc addBloc(Bloc bloc);
     void removeBloc(Long IdBloc);
     Bloc modifyBloc(Bloc bloc);
     List<Bloc> findBlocsByCapaciteMinimum(Long capaciteMin);
}
