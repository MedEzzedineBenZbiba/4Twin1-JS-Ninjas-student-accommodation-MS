package tn.esprit.tpfoyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.Universite;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversiteRepository extends JpaRepository<Universite, Long> {

    Optional<Universite> findByNomUniversite(String nomUniversite);

    @Query("SELECT u.adresse, COUNT(u) as count FROM Universite u GROUP BY u.adresse")
    List<Object[]> countUniversitesByAdresse();
}
