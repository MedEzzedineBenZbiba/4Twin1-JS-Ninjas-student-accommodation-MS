package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversityServiceImpl implements IUniversiteService{
    UniversiteRepository universiteRepository;
    @Override
    public List<Universite> retreiveAllUniversities() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite retrieveUniversity(Long universityId) {
        return universiteRepository.findById(universityId).get();
    }

    @Override
    public Universite addUniversity(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public void removeUniversity(Long universityId) {
        universiteRepository.deleteById(universityId);
    }

    @Override
    public Universite modifyUniversity(Universite universite) {
        return universiteRepository.save(universite);
    }
}
