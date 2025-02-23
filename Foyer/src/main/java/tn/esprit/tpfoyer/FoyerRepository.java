package tn.esprit.tpfoyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.Foyer;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {
}
