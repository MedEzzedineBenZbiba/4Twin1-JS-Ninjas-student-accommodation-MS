package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Bloc;

import java.util.List;

// @Repository to indicate that they are responsible for data access, typically interacting with a database.
@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {
//    List<Bloc> findAllByFoyerNull();
    


}
