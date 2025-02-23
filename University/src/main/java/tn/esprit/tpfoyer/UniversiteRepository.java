package tn.esprit.tpfoyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.Universite;

@Repository
public interface UniversiteRepository extends JpaRepository<Universite, Long> {
}
