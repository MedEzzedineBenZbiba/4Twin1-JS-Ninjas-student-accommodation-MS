package tn.esprit.tpfoyer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.Foyer;
import tn.esprit.tpfoyer.FoyerRepository;

@Service
public class FoyerSearchService {

    @Autowired
    private FoyerRepository foyerRepository;

    public Page<Foyer> searchFoyers(String keyword, String sortBy, String sortDir, Pageable pageable) {
        Specification<Foyer> spec = Specification.where(null);

        if(keyword != null) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("nomFoyer")), "%" + keyword.toLowerCase() + "%"),
                            cb.like(root.get("capaciteFoyer").as(String.class), "%" + keyword + "%")
                    ));
        }

        Sort sort = sortDir.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return foyerRepository.findAll(spec, PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        ));
    }
}