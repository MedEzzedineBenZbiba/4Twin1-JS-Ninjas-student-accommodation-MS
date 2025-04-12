package tn.esprit.tpfoyer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.Foyer;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long>, JpaSpecificationExecutor<Foyer> {
 }
