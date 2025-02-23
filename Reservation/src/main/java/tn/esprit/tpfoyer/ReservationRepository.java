package tn.esprit.tpfoyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
}
