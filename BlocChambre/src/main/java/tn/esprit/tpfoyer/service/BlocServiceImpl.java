package tn.esprit.tpfoyer.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BlocServiceImpl implements IBlocService{
    BlocRepository blocRepository;


    @Scheduled(fixedRate = 60000)
    @Override
    public List<Bloc> retrieveAllBlocs() {
        List<Bloc> blocs =  blocRepository.findAll();
        for(Bloc b : blocs) {
            log.info("Bloc : "  + b);
        }
        return blocs;
    }

    @Override
    public Bloc retrieveBloc(Long idBloc) {
        return blocRepository.findById(idBloc).get();
    }

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public void removeBloc(Long idBloc) {
        blocRepository.deleteById(idBloc);
    }

    @Override
    public Bloc modifyBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc AddBlocAndFoyerWithAffectation(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc AffectBlocToFoyer(Long idFoyer, Long idBloc) {
        return null;
    }

    @Override
    public Bloc desacffectBlocFromFoyer(Long idBloc) {
        return null;
    }

    @Override
    public List<Bloc> retrieveBlocsSansFoyer() {
        return null;
    }


//    @Override
//    public List<Bloc> retrieveBlocsSansFoyer() {
//        return blocRepository.findAllByFoyerNull();
//    }


}
